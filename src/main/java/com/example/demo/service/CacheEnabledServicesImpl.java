package com.example.demo.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.Utils;

@Service
public class CacheEnabledServicesImpl implements CacheEnabledServices {
	private static final Logger log = LoggerFactory.getLogger(CacheEnabledServicesImpl.class);
		
	@Override
	@Cacheable(value = CACHE_NAME, key = "#date")
	public File readFxRates(String date) {
		log.trace("in date = {}", date);
		File fxFile = Utils.downloadFxRate();
		log.debug("exit readFxRates date = {}, result = {}", date, fxFile);
		return fxFile;		
	}
	
}
