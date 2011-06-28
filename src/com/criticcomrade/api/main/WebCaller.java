package com.criticcomrade.api.main;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebCaller {
    
    private static Map<String, String> globalParameters = null;
    
    private static Map<String, String> getGlobalProperties() {
	if (globalParameters == null) {
	    globalParameters = new HashMap<String, String>();
	}
	try {
	    
	    Scanner in = new Scanner(new File("./src/com/criticcomrade/api/main/rottentomatoes.properties"));
	    while (in.hasNext()) {
		String[] line = in.nextLine().split("=");
		globalParameters.put(line[0], line[1]);
	    }
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("Unable to load rottentomatoes.properties file", e);
	}
	return globalParameters;
    }
    
    public static String doApiCall(String url, Map<String, String> params) throws IOException {
	
	params.putAll(getGlobalProperties());
	
	StringBuffer paramString = new StringBuffer();
	for (String param : params.keySet()) {
	    paramString.append("&").append(URLEncoder.encode(param)).append("=").append(URLEncoder.encode(params.get(param)));
	}
	
	URL apiUrl = new URL(url + "?" + paramString.toString());
	URLConnection connection = apiUrl.openConnection();
	Scanner scanner;
	try {
	    scanner = new Scanner(connection.getInputStream());
	} catch (IOException e) {
	    throw e;
	}
	scanner.useDelimiter("\\Z");
	
	return scanner.next();
	
    }
}
