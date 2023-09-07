package com.example.moviebuddy;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCinemaActivity extends AppCompatActivity {
    DatabaseManager dbManager;
    ListView list;
    Button addBtn,cancelBtn;
    EditText name,location;
    DatabaseManager mydManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydManager = new DatabaseManager(this);
        setContentView(R.layout.activity_add_cinema);
        onSetUpListeners();
//        onGetMoviesList();
    }

    public void onGetMoviesList(){
        dbManager = new DatabaseManager(AddCinemaActivity.this);
        dbManager.openReadable();
        ArrayList<String> values = dbManager.getMoviesList();
//        System.out.println("===== "+ values.toArray(new String[0]));

        // use your custom layout

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
//        list= (ListView) findViewById(R.id.moviesListView);
//        list.setAdapter(adapter);

//        CustomCinemaAdapter adapter = new CustomCinemaAdapter(this, values);
//        list= (ListView) findViewById(R.id.cinemasListView);
//        list.setAdapter(adapter);
    }
    public void onSetUpListeners(){
            name = findViewById(R.id.cinemaName);
            location = findViewById(R.id.location);
            addBtn = findViewById(R.id.saveCinema);
            cancelBtn = findViewById(R.id.cancelCinema);

        addBtn.setOnClickListener(v->{
            insertCinema();
//            showAlert(v);
        });
        cancelBtn.setOnClickListener(v->{
//            goback
            super.onBackPressed();
        });
    }



    public void insertCinema() {
        boolean isValid = this.isValid();
        if(!isValid){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Validation");
            alertDialog.setMessage("All fields are required.");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
            return;
        }
        else {
            mydManager = new DatabaseManager(this);
            String[] array = {"1","23"};
            Cinema cinema = new Cinema(name.getText().toString(),location.getText().toString(),array);
            mydManager.addCinema(cinema);
            Toast.makeText(getApplicationContext(), name.getText().toString() + " saved successfull.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isValid(){
        if(name.getText().toString().isEmpty() || location.getText().toString().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public void showAlert(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle("Confirm");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to save cinema ?")
                .setCancelable(false)
                .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
//                        MainActivity.this.finish();
                        insertCinema();
                    }
                })
                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


}