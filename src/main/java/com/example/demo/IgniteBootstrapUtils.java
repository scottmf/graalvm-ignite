package com.example.demo;

import org.apache.ignite.configuration.DataPageEvictionMode;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;

import java.util.List;

public class IgniteBootstrapUtils {
    static DataStorageConfiguration dataStorageConfiguration(List<DataRegionConfiguration> dataRegionConfigurations,
                                                             DataRegionConfiguration defaultDataRegion) {
        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
        dataStorageConfiguration.setDefaultDataRegionConfiguration(defaultDataRegion);
        dataStorageConfiguration
            .setDataRegionConfigurations(dataRegionConfigurations.toArray(new DataRegionConfiguration[0]));
        return dataStorageConfiguration;
    }

    static DataRegionConfiguration defaultDataRegion() {
        DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
        dataRegionConfiguration.setName("DefaultDataRegion");
        dataRegionConfiguration.setPersistenceEnabled(false);
        dataRegionConfiguration.setMetricsEnabled(true);
        dataRegionConfiguration.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);
        return dataRegionConfiguration;
    }

    public static int getContainerCpus(String containerCpus) {
        // check if cpus ends with 'm' which means milliCores as defined by k8s
        if (containerCpus.endsWith("m")) {
            containerCpus = containerCpus.substring(0, containerCpus.length() - 1);
            int cpus = Integer.parseInt(containerCpus) / 1000;
            return (cpus <= 1) ? 1 : cpus;
        }
        return Integer.parseInt(containerCpus);
    }

    public static int getCpusDividedByTwoMinTwo(int cpus) {
        return (cpus <= 4) ? 2 : cpus / 2;
    }

}
