package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils .class);
	private static final String URL_FX_RATE = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref.zip";

	public static File downloadFxRate() {
		File result = null;
		try {
			result = File.createTempFile(LocalDate.now().toString(), ".zip");
			FileUtils.copyURLToFile(
					new URL(URL_FX_RATE), 
					result 
					);
			log.debug("downloaded file {}", result);
			result = unzip(result.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static File unzip(String source){
	    String destPath = "/tmp/"+destination;
		try {
			ZipFile zipFile = new ZipFile(source);
	        	zipFile.extractAll(destPath);
	    } catch (ZipException | IOException e) {
	        e.printStackTrace();
	    }
	    return new File(destPath).listFiles()[0];	    
	}
	
	public static List<Map<String, String>> convert(File file) throws JsonProcessingException, IOException {
	    List<Map<String, String>> response = new LinkedList<Map<String, String>>();
	    CsvMapper mapper = new CsvMapper();
	    CsvSchema schema = CsvSchema.emptySchema().withHeader();
	    MappingIterator<Map<String, String>> iterator = mapper.reader(Map.class)
	            .with(schema)
	            .readValues(file);
	    while (iterator.hasNext()) {
	        response.add(iterator.next());
	    }
	    return response;
	}	

}
