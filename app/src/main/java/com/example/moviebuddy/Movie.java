package com.example.moviebuddy;

public class Movie {
    public Movie(String _name, String _director, String _casts, String _releaseDate){
        this.name = _name;
        this.director = _director;
        this.casts =_casts;
        this.releaseDate = _releaseDate;
    }
    private String name;
    private String releaseDate;
    private String director;
    private String casts;
    private String id;
    public void setId(String _id){
        this.id = _id;
    }
    public String getReleaseDate(){
        return this.releaseDate;
    }
    public String getDirector(){
        return this.director;

    }
    public String getCasts(){
        return this.casts;

    }
    public String getName(){
        return this.name;
    }
}
