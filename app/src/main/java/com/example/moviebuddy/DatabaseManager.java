package com.example.moviebuddy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseManager {
    public static final String DB_NAME = "MovieBuddy";
    public static final String DB_TABLE = "movie";
    public static final String DB_CINEMA_TABLE = "cinema";
    public static final int DB_VERSION = 1;
    public static final String GETMOVIESQUERY = "SELECT * FROM " + DB_TABLE + ";";
    public static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, director TEXT, moviecast TEXT, releaseDate TEXT);";
    public static String CINEMA_TABLE = "Cinema";
    public static final String CREATE_CINEMA = "CREATE TABLE " + DB_CINEMA_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, location TEXT, showingMoviesId TEXT);";


    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager openReadable() throws android.database.SQLException {

        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public DatabaseManager(Context c) {
//        System.out.println("***** CONSTRUCTOR CALLED IN DATABASE MANAGER CLASS ****");
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void addRow(Movie _movie) {
        ContentValues newMovie = new ContentValues();
//        newMovie.put("id",1);
        newMovie.put("name", _movie.getName());
        newMovie.put("director", _movie.getDirector());
        newMovie.put("moviecast", _movie.getCasts());
        newMovie.put("releaseDate", _movie.getReleaseDate());
        try {
            db.insertOrThrow(DB_TABLE, null, newMovie);
            System.out.println(newMovie);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }


    public boolean updateMovie(Movie _movie, String movieId) {
        System.out.println("MOVIE RECEIVED:" + _movie.toString() + "ID:" + movieId);
        // Assuming you have a SQLiteDatabase instance, for example, named "database"

// Define the new values you want to set
        ContentValues values = new ContentValues();
        values.put("name", _movie.getName());
        values.put("moviecast", _movie.getCasts());
        values.put("director", _movie.getDirector());
        values.put("releaseDate", _movie.getReleaseDate());
// Add more columns and their new values as needed

// Define the WHERE clause
        String whereClause = "id =" + movieId; // Replace with your condition
//        String[] whereArgs = { "value_to_match" }; // Replace with the value to match

// Perform the update
        int rowsAffected = db.update("movie", values, whereClause, null);

        if (rowsAffected > 0) {
            // The update was successful
            return true;
        } else {
            // No rows were updated (perhaps no matching rows found)
            return false;
        }


    }


//    public ArrayList<String> getMovies() {
//        ArrayList<String> movies = new ArrayList<>();
//        String[] columns = new String[] {"id","name", "director", "cast","releaseDate"};
//        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false) {
//            movies.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getFloat(2));
//            cursor.moveToNext();
//        }
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//        System.out.println(movies);
//        return new ArrayList<>();
//    }

    public ArrayList<String> retrieveRows() { //query the database and return records as a text
        ArrayList<String> productRows = new ArrayList<>();
        //String[] columns = new String[] {"id","name","director", "cast", "releaseDate"};
//        String[] columns = new String[]{"id","name","director","moviecast","releaseDate"};
        String[] columns = new String[]{"id", "name"};

        try {
            Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
//          Cursor cursor = db.rawQuery("SELECT * FROM "+ DB_TABLE ,null);
            cursor.moveToFirst();
            System.out.println("CURSOR count" + cursor.getCount());

            while (cursor.isAfterLast() == false) {
                System.out.println("ENTERED INSIDE LOOP");
//               productRows.add(cursor.getInt(0) + ", " + cursor.getString(1)+ ", " + cursor.getString(2)+ ", " + cursor.getString(3)+ ", " + cursor.getString(4));
                productRows.add(cursor.getInt(0) + ", " + cursor.getString(1));

                cursor.moveToNext();
                System.out.println("**PRODYCT" + productRows.toString());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if(productRows.isEmpty()){
                productRows.add("No records found");
            }
        } catch (Exception e) {
            System.out.println("ERROR HAS OCCURED:" + e);
        }
        System.out.println("THIS IS PRODUCT ROW FROM DATABASE" + productRows.toString());
        return productRows;
    }


    public Movie getMovieById(String movieId) {
        Movie _movie;


        // Define the SELECT query with a WHERE clause
        String query = "SELECT name, director, moviecast, releaseDate FROM movie WHERE id=" + movieId + ";";
        // Execute the query
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the Cursor
//                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String director = cursor.getString(cursor.getColumnIndex("director"));

                @SuppressLint("Range") String cast = cursor.getString(cursor.getColumnIndex("moviecast"));

                @SuppressLint("Range") String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));



                _movie = new Movie(name, director, cast, releaseDate);

            } while (cursor.moveToNext()); // Move to the next row
            cursor.close(); // Close the Cursor when done

        } else {
            _movie = new Movie(null, null, null, null);

        }

        System.out.println("MOVIE SENDINg" + _movie.toString());
        return _movie;

    }


    //    delete movie by id
    public boolean deleteMovieById(String movieId) {
        System.out.println("MOVIE RECEIVED:ID:" + movieId);
        // Assuming you have a SQLiteDatabase instance, for example, named "database"


// Define the WHERE clause
        String whereClause = "id =" + movieId; // Replace with your condition
//        String[] whereArgs = { "value_to_match" }; // Replace with the value to match

// Perform the update
        int rowsAffected = db.delete("movie", whereClause, null);

        if (rowsAffected > 0) {
            // The update was successful
            return true;
        } else {
            // No rows were updated (perhaps no matching rows found)
            return false;
        }


    }

    public void addCinema(Cinema cinema) {
        ContentValues newCinema = new ContentValues();
        newCinema.put("name", cinema.getName());
        newCinema.put("location", cinema.getLocation());
        newCinema.put("showingMoviesId", "");

        try {
            db.insertOrThrow(DB_CINEMA_TABLE, null, newCinema);
            System.out.println(newCinema);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();

        }
    }


    public ArrayList<String> getMoviesList() { //query the database and return records as a text
        ArrayList<String> productRows = new ArrayList<>();
        String[] columns = new String[]{"id", "name"};

        try {
            Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
            cursor.moveToFirst();
            System.out.println("CURSOR count" + cursor.getCount());

            while (cursor.isAfterLast() == false) {

//               productRows.add(cursor.getInt(0) + ", " + cursor.getString(1)+ ", " + cursor.getString(2)+ ", " + cursor.getString(3)+ ", " + cursor.getString(4));
                productRows.add(cursor.getInt(0) + ", " + cursor.getString(1));

                cursor.moveToNext();
//                System.out.println("**PRODYCT" + productRows.toString());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if(productRows.isEmpty()){
                productRows.add("No movies found");
            }
        } catch (Exception e) {
            System.out.println("ERROR HAS OCCURED:" + e);
        }
//        System.out.println("THIS IS PRODUCT ROW FROM DATABASE" + productRows.toString());
        return productRows;
    }

    public ArrayList<String> getCinemasList(){
        ArrayList<String> productRows = new ArrayList<>();
        String[] columns = new String[]{"id", "name","location"};

        try {
            Cursor cursor = db.query(DB_CINEMA_TABLE, columns, null, null, null, null, null);
            cursor.moveToFirst();
            System.out.println("CURSOR count IN DBMN" + cursor.getCount());

            while (cursor.isAfterLast() == false) {
//");
               productRows.add(cursor.getInt(0) + ", " + cursor.getString(1));
//                productRows.add(cursor.getInt(0) + ", " + cursor.getString(1)+ ", " + cursor.getString(2));

                cursor.moveToNext();
                System.out.println("**PRODYCT" + productRows.toString());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if(productRows.isEmpty()){
                productRows.add("No records found");
            }
        } catch (Exception e) {
            System.out.println("ERROR HAS OCCURED:" + e);
        }
//        System.out.println("THIS IS PRODUCT ROW FROM DATABASE" + productRows.toString());
        return productRows;
    }


    public Cinema getCinemaById(String cinemaId) {
        Cinema _data;


        // Define the SELECT query with a WHERE clause
        String query = "SELECT name, location, showingMoviesId FROM cinema WHERE id=" + cinemaId + ";";
        // Execute the query
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the Cursor
//                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex("location"));

                @SuppressLint("Range") String showingMoviesId = cursor.getString(cursor.getColumnIndex("showingMoviesId"));


                System.out.println("MOVIES ID -> "+showingMoviesId);




                _data = new Cinema(name,location,new ArrayList<String>(Arrays.asList(showingMoviesId.split(""))));
                //                _data = new Cinema(name,location,new ArrayList<>(Arrays.asList(showingMoviesId.split(""))));


            } while (cursor.moveToNext()); // Move to the next row
            cursor.close(); // Close the Cursor when done

        } else {
            _data = new Cinema(null, null, null);

        }

        System.out.println("MOVIE SENDINg" + _data.toString());
        return _data;

    }

    public boolean updateCinema(Cinema cinema, String cinemaId) {
        System.out.println("MOVIE RECEIVED:" + cinema.toString() + "ID:" + cinemaId);
        // Assuming you have a SQLiteDatabase instance, for example, named "database"

// Define the new values you want to set
        ContentValues values = new ContentValues();
        values.put("name", cinema.getName());
        values.put("location", cinema.getLocation());
        System.out.println("SHOWING IDS IS SAVING"+cinema.getNowShowing().toString());
        values.put("showingMoviesId",cinema.getNowShowing().toString());
//        values.put("director", _movie.getDirector());
//        values.put("releaseDate", _movie.getReleaseDate());
// Add more columns and their new values as needed

// Define the WHERE clause
        String whereClause = "id =" + cinemaId; // Replace with your condition
//        String[] whereArgs = { "value_to_match" }; // Replace with the value to match

// Perform the update
        int rowsAffected = db.update("cinema", values, whereClause, null);

        if (rowsAffected > 0) {
            // The update was successful
            return true;
        } else {
            // No rows were updated (perhaps no matching rows found)
            return false;
        }


    }
    public ArrayList<String>  getMoviesByCinema(String cinemaId){
         //query the database and return records as a text
            ArrayList<String> moviesRows = new ArrayList<String>();
            String[] columns = new String[]{"showingMoviesId"};

            try {
                Cursor cursor = db.query(DB_CINEMA_TABLE, columns, "id="+cinemaId, null, null, null, null);
                cursor.moveToFirst();
                System.out.println("CURSOR count" + cursor.getCount());

                while (cursor.isAfterLast() == false) {

//               productRows.add(cursor.getInt(0) + ", " + cursor.getString(1)+ ", " + cursor.getString(2)+ ", " + cursor.getString(3)+ ", " + cursor.getString(4));
                    moviesRows.add(cursor.getString(0));

                    cursor.moveToNext();
//                System.out.println("**PRODYCT" + productRows.toString());
                }
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                if(moviesRows.isEmpty()){
                    moviesRows.add("No movies found");
                }
            } catch (Exception e) {
                System.out.println("ERROR HAS OCCURED:" + e);
            }
//        System.out.println("THIS IS PRODUCT ROW FROM DATABASE" + productRows.toString());
        System.out.println(("RETURNING IN "+moviesRows));
            return moviesRows;
        }

    public ArrayList<String> getMoviesListForCheckBox() { //query the database and return records as a text
        ArrayList<String> productRows = new ArrayList<>();
        String[] columns = new String[]{"name"};

        try {
            Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
            cursor.moveToFirst();
            System.out.println("CURSOR count" + cursor.getCount());

            while (cursor.isAfterLast() == false) {

//               productRows.add(cursor.getInt(0) + ", " + cursor.getString(1)+ ", " + cursor.getString(2)+ ", " + cursor.getString(3)+ ", " + cursor.getString(4));
                productRows.add(cursor.getString(0) );


                cursor.moveToNext();
//                System.out.println("**PRODYCT" + productRows.toString());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if(productRows.isEmpty()){
                productRows.add("No movies found");
            }
        } catch (Exception e) {
            System.out.println("ERROR HAS OCCURED:" + e);
        }
//        System.out.println("THIS IS PRODUCT ROW FROM DATABASE" + productRows.toString());
        return productRows;
    }

    }










