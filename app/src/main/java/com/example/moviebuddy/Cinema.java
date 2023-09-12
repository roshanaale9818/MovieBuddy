package com.example.moviebuddy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cinema {
    public Cinema(String _name, String _location, ArrayList<String>_nowShowing){
        this.location = _location;
        this.name= _name;
//        this.showingMovies =_showingMovies;
        this.nowShowing = _nowShowing;
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

    public ArrayList<String> getNowShowing(){
        return this.nowShowing;
    }
    public ArrayList<String> nowShowing = new ArrayList<String>();

}
