package com.example.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSystemProperties;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.isolated.IsolatedDiscoverySpi;
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
        System.setProperty(IgniteSystemProperties.IGNITE_LOCAL_HOST, "localhost");

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("graalvm");
        cfg.setIgniteHome(igniteHome);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setIncludeEventTypes(new int[] { EVT_CACHE_STARTED });

        cfg.setGridLogger(new Slf4jLogger());
        cfg.setDiscoverySpi(new IsolatedDiscoverySpi());
        cfg.setCommunicationSpi(null);
        cfg.setClientMode(false);

        return cfg;
    }

    @Bean
    @ConditionalOnMissingBean
    public Ignite ignite(IgniteConfiguration igniteConfiguration, ConfigurableApplicationContext context) {
        System.setProperty(IgniteSystemProperties.IGNITE_UPDATE_NOTIFIER, "false");

        // We don't need Ignite's shutdown hook because Spring will take care of cleanup
        // for us
        System.setProperty(IgniteSystemProperties.IGNITE_NO_SHUTDOWN_HOOK, "true");

        return Ignition.getOrStart(igniteConfiguration);
    }

}
