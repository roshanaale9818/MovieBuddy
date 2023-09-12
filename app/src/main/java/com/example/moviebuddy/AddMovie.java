package com.example.moviebuddy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class AddMovie extends AppCompatActivity {
Button addBtn,cancelBtn,viewAllBtn;
EditText name,director,releaseDate,casts;
    DatabaseManager mydManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("***** ADD MOVIE IS INIT*****");
        setContentView(R.layout.activity_add_movie);
        mydManager = new DatabaseManager(this);
        setUpEventListnerMethods();


    }


    public void showErrorAlert(String labelName){
        AlertDialog.Builder alertDialog = new
                AlertDialog.Builder(this);
        alertDialog.setTitle("Validation");
        alertDialog.setMessage(labelName +" is required.");
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();

    }

    Button home;

    private void setUpEventListnerMethods(){
        addBtn = findViewById(R.id.saveBtn);
        viewAllBtn = findViewById(R.id.viewAllBtn);
        name = findViewById(R.id.name);
        director = findViewById(R.id.directorEditText);
        casts = findViewById(R.id.castEditText);
        releaseDate = findViewById(R.id.releaseDateEditText);
        cancelBtn = findViewById(R.id.cancelBtn);
//        home = findViewById(R.id.onHome);


        addBtn.setOnClickListener(v->{
//            insertRows();
            showAlert(v);
        });
        cancelBtn.setOnClickListener(v->{
//            goback
            super.onBackPressed();
        });
//        home.setOnClickListener(v->{
//            Intent intent = new Intent(AddMovie.this
//                    ,MainActivity.class);
//            startActivity(intent);
//        });
        viewAllBtn.setOnClickListener(v->{
            Intent intent = new Intent(AddMovie.this
                    ,ListMovie.class);
            startActivity(intent);
        });


    }
    public void getRows(){
//        String movies = mydManager.getMovies();
//        System.out.println(movies);
    }

    Button btn;
    public void insertRows() {
        boolean isValid = this.isValid();
        if(!isValid){
            AlertDialog.Builder alertDialog = new
                    AlertDialog.Builder(this);
            alertDialog.setTitle("Validation");
            alertDialog.setMessage("All fields are required.");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
            return;
        }
        else {
            mydManager = new DatabaseManager(this);
//        Movie movie = new Movie("Openheimer","Christian Bale","Cilian Murphy","2016/08/30");
            Movie movie = new Movie(name.getText().toString(), director.getText().toString(), casts.getText().toString(), releaseDate.getText().toString());
            mydManager.addRow(movie);
            Toast.makeText(getApplicationContext(), name.getText().toString() + " saved Successfull.", Toast.LENGTH_SHORT).show();
//        mydManager.close();
            ;
        }
    }


    private boolean isValid(){
        if(name.getText().toString().isEmpty() || releaseDate.getText().toString().isEmpty() ||
                casts.getText().toString().isEmpty() || director.getText().toString().isEmpty()){
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
                .setMessage("Are you sure you want to save movie ?")
                .setCancelable(false)
                .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
//                        MainActivity.this.finish();
                        insertRows();
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