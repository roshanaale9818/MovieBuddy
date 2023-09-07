package com.example.moviebuddy;

public class Cinema {
    public Cinema(String _name, String _location, String[] _showingMovies){
        this.location = _location;
        this.name= _name;
        this.showingMovies =_showingMovies;
    }

    private String name;
    private String location;
    private String[] showingMovies;

    public String getName(){
        return this.name;
    }
    public String getLocation(){
        return this.location;
    }
    public String[] getShowingMovies(){
        return this.showingMovies;
    }


}
