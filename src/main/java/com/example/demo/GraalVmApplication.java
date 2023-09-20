package com.example.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteSystemProperties;
import org.apache.ignite.Ignition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraalVmApplication {

	public static void main(String[] args) {
		System.setProperty(IgniteSystemProperties.IGNITE_LOCAL_HOST, "localhost");
		SpringApplication.run(GraalVmApplication.class, args);
		Ignite ignite = Ignition.start();
		IgniteCache<Integer, Integer> myCache = ignite.getOrCreateCache("my-cache");
		for (int i = 0; i < 100; i++) {
			myCache.put(i, i);
		}
		for (int i = 0; i < 100; i++) {
			System.err.println(myCache.get(i));
		}
	}

}
