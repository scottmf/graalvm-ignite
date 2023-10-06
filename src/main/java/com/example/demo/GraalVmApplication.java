package com.example.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteSystemProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GraalVmApplication {

	public static void main(String[] args) {
		System.setProperty(IgniteSystemProperties.IGNITE_LOCAL_HOST, "localhost");
		ConfigurableApplicationContext ctx = SpringApplication.run(GraalVmApplication.class, args);
		Ignite ignite = ctx.getBean(Ignite.class);
		IgniteCache<Integer, Integer> myCache = ignite.getOrCreateCache("my-cache");
		for (int i = 0; i < 100; i++) {
			myCache.put(i, i);
		}
		for (int i = 0; i < 100; i++) {
			System.err.println(myCache.get(i));
		}
		System.exit(0);
	}

}
