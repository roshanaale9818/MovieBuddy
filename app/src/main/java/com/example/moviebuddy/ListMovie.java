package com.example.moviebuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMovie extends AppCompatActivity {
ListView list;
DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);
        System.out.println("LINE IS CALLED");
        dbManager = new DatabaseManager(ListMovie.this);
        dbManager.openReadable();


       ArrayList<String> values = dbManager.retrieveRows();
       System.out.println("===== "+ values.toString());

        // use your custom layout
//        MovieAdapter adapter = new MovieAdapter(this, values);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
//        productRecord.setAdapter(adapter);
        list= (ListView) findViewById(R.id.moviesListView);
        list.setAdapter(adapter);

        //uncomment line 35 to enable choosing multiple item from the list
        // list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        list.setOnItemClickListener((parent, v, position, id) -> {
//            //uncomment line 40 to highlight item choosed when CHOICE_MODE_MULTIPLE is on
//            //       v.setBackgroundResource(R.drawable.ic_launcher_background);
//            String item = (String) list.getAdapter().getItem(position);
//            Toast.makeText(getApplicationContext(), item + " selected", Toast.LENGTH_LONG).show();
//        });

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