package com.example.moviebuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCinemaActivity extends AppCompatActivity {

    ListView list;
    DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cinema);
        System.out.println("LINE IS CALLED");
        dbManager = new DatabaseManager(ListCinemaActivity.this);
        dbManager.openReadable();


        ArrayList<String> values = dbManager.getCinemasList();
        System.out.println("===== "+ values.toArray(new String[0]));

        // use your custom layout

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
//        list= (ListView) findViewById(R.id.moviesListView);
//        list.setAdapter(adapter);

        CustomCinemaAdapter adapter = new CustomCinemaAdapter(this, values);
        list= (ListView) findViewById(R.id.cinemasListView);
        list.setAdapter(adapter);


    }

//    public boolean showRows() {
//        mydManager = new DatabaseManager(this);
//        mydManager.openReadable();
//        String tableContent = mydManager.retrieveRows();
//        binding.response.setText("The rows in the products table are: ");
//        binding.prodrec.setText(tableContent);
//        mydManager.close();
//        return true;
//    }

}