package com.example.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSystemProperties;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.processors.cache.persistence.wal.reader.StandaloneNoopCommunicationSpi;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinderAdapter;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static org.apache.ignite.events.EventType.EVT_CACHE_STARTED;

@Configuration(proxyBeanMethods = false)
public class IgniteNoClusterConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int ignitePort = findFreePort();

    @Bean(name = "igniteIpFinder")
    public TcpDiscoveryVmIpFinder igniteIpFinder() {
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singleton(format("127.0.0.1:%s", ignitePort)));
        return ipFinder;
    }

    // Temporal implementation
    private static int findFreePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to find a free port", ex);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public IgniteConfiguration igniteConfiguration(
        @Value("#{'${IGNITE_HOME:}' ?: systemProperties['java.io.tmpdir']}") String igniteHome) {

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("graalvm");
//        dataStorageConfiguration.ifPresent(dss -> cfg.setDataStorageConfiguration(dss));
        cfg.setIgniteHome(igniteHome);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setIncludeEventTypes(new int[] { EVT_CACHE_STARTED });

        cfg.setGridLogger(new Slf4jLogger());
//        cfg.setDiscoverySpi(tcpDiscoverySpi);
        cfg.setCommunicationSpi(new StandaloneNoopCommunicationSpi());
        cfg.setClientMode(false);
//        cfg.setDataStreamerThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setPublicThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setSystemThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setStripedPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setManagementThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setServiceThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setQueryThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
//        cfg.setPeerClassLoadingThreadPoolSize(symphonyIgniteProperties.getThreadPoolSize());
        // setting ConnectorConfiguration to null removes these thread pools:
        // "grid-nio-worker-tcp-rest-x", "nio-acceptor-tcp-rest-#18%<instance_name>%"
        // and "session-timeout-worker-#13%<instance_name>%"
//        cfg.setConnectorConfiguration(null);
//        cfg.setClientConnectorConfiguration(null);
        // since this is not forming a cluster we'll minimize this value
//        cfg.setRebalanceThreadPoolSize(1);

        return cfg;
    }

//    @Bean
    @ConditionalOnMissingBean
    public TcpDiscoverySpi tcpDiscoverySpi(TcpDiscoveryIpFinderAdapter igniteIpFinder) {
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        log.info("using igniteIpFinder class {}", igniteIpFinder.getClass());
        tcpDiscoverySpi.setIpFinder(igniteIpFinder);
        tcpDiscoverySpi.setLocalPortRange(0);
        tcpDiscoverySpi.setLocalPort(ignitePort);
        String loopbackAddress = InetAddress.getLoopbackAddress().getHostAddress();
        tcpDiscoverySpi.setLocalAddress(loopbackAddress);
        return tcpDiscoverySpi;
    }

    @Bean
    @ConditionalOnMissingBean
    public Ignite ignite(IgniteConfiguration igniteConfiguration, ConfigurableApplicationContext context) {
//        System.setProperty(IgniteSystemProperties.IGNITE_QUIET,
//            String.valueOf(symphonyIgniteProperties.isIgniteQuiet()));
        System.setProperty(IgniteSystemProperties.IGNITE_UPDATE_NOTIFIER, "false");

        // We don't need Ignite's shutdown hook because Spring will take care of cleanup
        // for us
        System.setProperty(IgniteSystemProperties.IGNITE_NO_SHUTDOWN_HOOK, "true");

        // Leads to a significant speedup when starting ignite with multiple interfaces
        // see IgniteUtils#getLocalHost for more information
//        if (StringUtils.hasText(symphonyIgniteProperties.getLocalhost())) {
//            System.setProperty(IgniteSystemProperties.IGNITE_LOCAL_HOST, symphonyIgniteProperties.getLocalhost());
//        }

        return Ignition.getOrStart(igniteConfiguration);
//        String cachePrefix = symphonyIgniteProperties.getIgniteCachePrefix();
//        return IgniteProxyFactory.create(ignite, cachePrefix, new IgniteErrorHandler(context));
    }

//    @Bean
    @ConditionalOnMissingBean
    public DataStorageConfiguration dataStorageConfiguration(
        // @Qualifier needed to distinguish single DataRegionConfiguration which is
        // used as the default region vs
        // list of DataRegionConfiguration which is used for general data regions
        // configurations.
        @Qualifier("dataRegionConfigurations") List<DataRegionConfiguration> dataRegionConfigurations,
        @Qualifier("defaultDataRegion") DataRegionConfiguration defaultDataRegion) {
        return IgniteBootstrapUtils.dataStorageConfiguration(dataRegionConfigurations, defaultDataRegion);
    }

//    @Bean
    @ConditionalOnMissingBean
    public DataRegionConfiguration defaultDataRegion() {
        return IgniteBootstrapUtils.defaultDataRegion();
    }

//    @Bean
    @ConditionalOnMissingBean
    public List<DataRegionConfiguration> dataRegionConfigurations() {
        return Collections.emptyList();
    }

}
