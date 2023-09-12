package com.example.moviebuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMovie extends AppCompatActivity {
ListView list;
DatabaseManager dbManager;
Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);
        System.out.println("LINE IS CALLED");
        dbManager = new DatabaseManager(ListMovie.this);
        dbManager.openReadable();


       ArrayList<String> values = dbManager.retrieveRows();


        CustomMovieAdapter adapter = new CustomMovieAdapter(this, values);
        list= (ListView) findViewById(R.id.moviesListView);
        list.setAdapter(adapter);



    }


}