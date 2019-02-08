package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CacheEnabledServices {
	public static final String CACHE_NAME = "fxRates";
	/**
	 * return CSV file with all fx rates for a given date
	 * @param date
	 * @return File
	 *  
	 */
	File readFxRates(String date);

}
