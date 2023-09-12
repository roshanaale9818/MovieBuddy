package com.example.moviebuddy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CinemaView extends AppCompatActivity {
    ArrayList  showingMovies = new ArrayList<String>();
    ListView list;
    Button addBtn,cancelBtn;
    EditText name,location;
    DatabaseManager mydManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_view);
        mydManager = new DatabaseManager(this);
        setUpEventListenerMethods();
        setUpCheckboxes();
    }
    ArrayList savedShows = new ArrayList<String>();
    public void setUpCheckboxes(){
        LinearLayout container = findViewById(R.id.container);

        ArrayList _listMovies =  mydManager.getMoviesListForCheckBox();
        savedShows = mydManager.getMoviesByCinema(currentCinemaId);
        System.out.println("savedShows,"+savedShows);
        _listMovies.forEach(movie->{
//            String name = movie;
            CheckBox checkBox = new CheckBox(this);
                checkBox.setText(movie.toString());
                container.addView(checkBox);




            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                       if(isChecked){
                                                           System.out.println("IS CHECKED"+movie.toString());
                                                           String name = movie.toString();
//                                                           String[] values = name.split(",");
                                                           showingMovies.add(name);

                                                       }
                                                       else{
                                                           System.out.println("NOT CHECKED");
                                                           showingMovies.removeIf(_data-> _data==movie);
                                                       }
                                                       System.out.println("ARRAy"+showingMovies);

                                                   }
                                               }
            );

            savedShows.forEach(_data -> {
//                System.out.println("REMOVE SLASH"+ );

//                System.out.println(_data + " "+checkBox.getText());

                String removedData = _data.toString().replaceAll("\\[|\\]", "");
                if(checkBox.getText().toString().equals(removedData)){
                    checkBox.setChecked(true);
                }
            });

//            savedShows.forEach(_savedMovie->{
////                System.out.println("INSIDE LOOP->" +checkBox.getText()+""+movie.toString().replaceAll("\\[|\\]", ""));
////                System.out.println("IS IT TRUE OR FALSE"+checkBox.getText()==movie.toString().replaceAll("\\[|\\]", ""));
//                if(checkBox.getText().equals(movie.toString().replaceAll("\\[|\\]", ""))){
//                    System.out.println("CHECKED TRUE");
//                    checkBox.setChecked(true);
//                }
//            });

        });
//        for (int data=0;data<_listMovies.size();data++){
//            CheckBox  = new CheckBox(this);
//        }


        // Create checkboxes dynamically
//        CheckBox checkbox1 = new CheckBox(this);
//        checkbox1.setText("Checkbox 1");
//        container.addView(checkbox1);
//        checkbox1.setElevation(1);
//
//        CheckBox checkbox2 = new CheckBox(this);
//        checkbox2.setText("Checkbox 2");
//        container.addView(checkbox2);
    }
    private void setUpEventListenerMethods(){
        name = findViewById(R.id.cinemaName);
        location = findViewById(R.id.cineLocation);
        addBtn = findViewById(R.id.editCinema);
        cancelBtn = findViewById(R.id.cancelEditCinema);
        addBtn.setOnClickListener(v->{
            updateCinema();

        });
        cancelBtn.setOnClickListener(v->{
            Intent intent = new Intent(CinemaView.this
                    ,MainActivity.class);
            startActivity(intent);
        });
        getDatabaseValuesAndFillUpForm();

    }


    String  currentCinemaId;
    private void getDatabaseValuesAndFillUpForm(){
        currentCinemaId = getIntent().getStringExtra("cinemaId");
//        System.out.println("MOVID"+currentCinemaId);
        if(currentCinemaId.isEmpty()){
            return;
        }
        else{
//            Cinema data = new Cinema("","",new String[]{""});
            Cinema data = mydManager.getCinemaById(currentCinemaId);
//            System.out.println("AAYO DATA"+data);
            name.setText(data.getName());
            location.setText(data.getLocation());
//            director.setText((data.getDirector()));
//            releaseDate.setText(data.getReleaseDate());
        }
    }


    public void updateCinema() {
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
            Cinema data = new Cinema(name.getText().toString(), location.getText().toString(),showingMovies);
            boolean isAdded =  mydManager.updateCinema(data,currentCinemaId);
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
        if(name.getText().toString().isEmpty() || location.getText().toString().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}