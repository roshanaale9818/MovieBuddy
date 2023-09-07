package com.example.moviebuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MovieView extends AppCompatActivity {
    Button addBtn,cancelBtn,deletBtn;
    EditText name,director,releaseDate,casts;
    DatabaseManager mydManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println("***** ADD MOVIE IS INIT*****");
        setContentView(R.layout.activity_movie_view);
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

    private void setUpEventListnerMethods(){
        mydManager = new DatabaseManager(MovieView.this);
        mydManager.openReadable();
        addBtn = findViewById(R.id.saveBtn);
//        viewAllBtn = findViewById(R.id.viewAllBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        deletBtn = findViewById(R.id.deleteMovieView);
        name = findViewById(R.id.name);
        director = findViewById(R.id.directorEditText);
        casts = findViewById(R.id.castEditText);
        releaseDate = findViewById(R.id.releaseDateEditText);

        getDatabaseValuesAndFillUpForm();


        addBtn.setOnClickListener(v->{
            showAlert(v);
        });
        cancelBtn.setOnClickListener(v->{
            goBack();
        });
        deletBtn.setOnClickListener(v->{
            deleteMovie(name.getText().toString(),currentMovieId);
        });


    }
    String  currentMovieId;
    private void getDatabaseValuesAndFillUpForm(){
        currentMovieId = getIntent().getStringExtra("movieId");
        System.out.println("MOVID"+currentMovieId);
        if(currentMovieId.isEmpty()){
                return;
        }
       else{
            Movie data = mydManager.getMovieById(currentMovieId);
            System.out.println("AAYO DATA"+data);
            name.setText(data.getName());
            casts.setText(data.getCasts());
            director.setText((data.getDirector()));
            releaseDate.setText(data.getReleaseDate());
        }
    }
    private void goBack(){
//        super.onBackPressed();
        Intent intent = new Intent(MovieView.this
                ,ListMovie.class);
        startActivity(intent);
    }
    public void getRows(){
//        String movies = mydManager.getMovies();
//        System.out.println(movies);
    }

    Button btn;
    public void updateMovie() {
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
           boolean isAdded =  mydManager.updateMovie(movie,currentMovieId);
           if(isAdded){
               Toast.makeText(getApplicationContext(), name.getText().toString() + " updated successfull.", Toast.LENGTH_SHORT).show();

           }
           else{
               Toast.makeText(getApplicationContext(), name.getText().toString() + "Something went wrong.", Toast.LENGTH_SHORT).show();

           }
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
                .setMessage("Are you sure you want to update movie ?")
                .setCancelable(false)
                .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
//                        MainActivity.this.finish();
//                        insertRows();
                        updateMovie();
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





    public void deleteMovie(String movieName, String movieId){
        mydManager = new DatabaseManager(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle("Confirm");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete "+movieName+"?")
                .setCancelable(false)
                .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
//                        MainActivity.this.finish();
//                        insertRows();
             boolean isDeleted = mydManager.deleteMovieById(movieId);
             if(isDeleted){
                 Toast.makeText(getApplicationContext(), ""+ " Delete successfull.", Toast.LENGTH_SHORT).show();
                 goBack();

             }
             else{
                 Toast.makeText(getApplicationContext(), "Something went wrong"+ " Failed", Toast.LENGTH_SHORT).show();

             }
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