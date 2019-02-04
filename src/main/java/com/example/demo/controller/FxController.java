package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils;
import com.example.demo.service.CacheEnabledServices;

@RestController
public class FxController {
	private static final Logger LOG = LoggerFactory.getLogger(FxController.class);
	
	public final static String PATH_BASE = "/currencies";
	
	@Autowired
	private CacheEnabledServices cache;
	
	/**
	 * GET '/currencies' to return csv file
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	@RequestMapping(value = PATH_BASE)
	@ResponseBody
	public ResponseEntity<InputStreamResource> asCSV(HttpServletResponse response) throws Exception {         
		LOG.debug("in asCSV");
		File csv = cache.readFxRates(LocalDate.now().toString());
		  InputStreamResource resource = new InputStreamResource(new FileInputStream(csv));
		  return ResponseEntity.ok()
		            //.headers(headers)
		            .contentLength(csv.length())
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
	}
	
	/**
	 * returns rate for a given symbol
	 * @return
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = PATH_BASE+"/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> getFxRates(@PathVariable String symbol) throws Exception {
		LOG.debug("in getFxRates");
		File csvFile = cache.readFxRates(LocalDate.now().toString());
		Map<String, String> map = Utils.convert(csvFile).get(0);
		if(map.get(symbol.toUpperCase()) == null)
			return ResponseEntity.badRequest().body("No rate found for SYMBOL " + symbol);
		return ResponseEntity.ok(map.get(symbol));
	}
}
