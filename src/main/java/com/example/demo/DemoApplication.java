package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import static com.example.demo.service.CacheEnabledServices.*;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	CacheManager cacheManager;
	 
	// every night 12'o clock
	@Scheduled(cron = "*/10 * * * * ?")
	@CacheEvict(value = { CACHE_NAME })
	public void clearCache() {      
		cacheManager.getCache(CACHE_NAME).clear();
		log.debug("cache cleared {}", CACHE_NAME );
	}
}

