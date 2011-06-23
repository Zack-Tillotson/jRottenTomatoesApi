package com.criticcomrade.api;

import java.io.IOException;
import java.net.*;
import java.util.*;

import com.criticcomrade.api.data.*;
import com.google.gson.*;

public class RottenTomatoesApi {
    
    private static final String URL_API_HOME = "http://api.rottentomatoes.com/api/public/v1.0/";
    private static final String URL_SEARCH_MOVIES = URL_API_HOME + "movies.json";
    private static final String URL_MOVIE = URL_API_HOME + "movies/<movie_id>.json";
    private static final String URL_MOVIE_REVIEWS = URL_API_HOME + "movies/<movie_id>/reviews.json";
    
    /**
     * Query the Rotten Tomatoes public API.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	
	List<MovieShort> searchResults = RottenTomatoesApi.searchMovies("Name");
	for (MovieShort ms : searchResults) {
	    
	    Movie m = RottenTomatoesApi.getMovie(ms);
	    
	    System.out.println(m.title);
	    
	    List<Review> reviews = RottenTomatoesApi.getReviews(m);
	    for (Review r : reviews) {
		
		System.out.println("\t" + r.critic + " at " + r.publication + " = " + r.original_score);
		
	    }
	    
	    System.out.println("\n");
	}
	
    }
    
    public static List<MovieShort> searchMovies(String title) throws IOException {
	
	String url = URL_SEARCH_MOVIES;
	
	Map<String, String> params = new HashMap<String, String>();
	params.put("q", title);
	params.put("page_limit", "50");
	params.put("page", "1");
	
	MovieSearchResults ret = (new Gson()).fromJson(doApiCall(url, params), MovieSearchResults.class);
	
	return new ArrayList<MovieShort>(ret.movies);
	
    }
    
    public static Movie getMovie(MovieShort ms) throws JsonSyntaxException, IOException {
	return getMovie(ms.id);
    }
    
    public static Movie getMovie(String id) throws JsonSyntaxException, IOException {
	
	String url = URL_MOVIE.replaceAll("<movie_id>", id);
	
	Movie ret = (new Gson()).fromJson(doApiCall(url, new HashMap<String, String>()), Movie.class);
	
	return ret;
	
    }
    
    public static List<Review> getReviews(Movie m) throws JsonSyntaxException, IOException {
	String url = URL_MOVIE_REVIEWS.replace("<movie_id>", m.id);
	ReviewList ret = (new Gson()).fromJson(doApiCall(url, new HashMap<String, String>()), ReviewList.class);
	return new ArrayList<Review>(ret.reviews);
    }
    
    private static String doApiCall(String url, Map<String, String> params) throws IOException {
	
	params.put("apikey", "");
	
	StringBuffer paramString = new StringBuffer();
	for (String param : params.keySet()) {
	    paramString.append("&").append(URLEncoder.encode(param)).append("=").append(URLEncoder.encode(params.get(param)));
	}
	
	URL apiUrl = new URL(url + "?" + paramString.toString());
	URLConnection connection = apiUrl.openConnection();
	Scanner scanner = new Scanner(connection.getInputStream());
	scanner.useDelimiter("\\Z");
	
	return scanner.next();
	
    }
}
