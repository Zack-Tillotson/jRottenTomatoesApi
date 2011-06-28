package com.criticcomrade.api.data;

import java.util.Collection;

public class Movie extends MovieShort {
    
    public String mpaa_rating;
    public String runtime;
    public ReleaseDates release_dates;
    public String synopsis;
    public PosterLinks posters;
    public Collection<String> genres;
    public Collection<MoviePerson> abridged_cast;
    public Collection<MoviePerson> abdridged_directors;
    public AlternateIds alternate_ids;
    
}
