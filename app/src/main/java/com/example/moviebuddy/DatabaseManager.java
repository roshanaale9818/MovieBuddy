package com.example.moviebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "MovieBuddy";
    public static final String DB_TABLE = "movie";
    public static final String DB_CINEMA_TABLE ="cinema";
    public static final int DB_VERSION = 1;
    public static final String GETMOVIESQUERY= "SELECT * FROM "+ DB_TABLE+";";
    public static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, director TEXT, cast TEXT, releaseDate TEXT);";

    public static final String CREATE_TABLE_CINEMA = "CREATE TABLE " + DB_CINEMA_TABLE
            + " (name TEXT,director TEXT,cast TEXT, releaseDate TEXT);";


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
        System.out.println("***** CONSTRUCTOR CALLED IN DATABASE MANAGER CLASS ****");
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void addRow(Movie _movie) {
        ContentValues newMovie = new ContentValues();
//        newMovie.put("id",1);
        newMovie.put("name", _movie.getName());
        newMovie.put("director", _movie.getDirector());
        newMovie.put("cast", _movie.getCasts());
        newMovie.put("releaseDate",_movie.getReleaseDate());
        try {
            db.insertOrThrow(DB_TABLE, null, newMovie);
            System.out.println(newMovie);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }



    public ArrayList<String> getMovies() {
        ArrayList<String> movies = new ArrayList<>();
        String[] columns = new String[] {"id","name", "director", "cast","releaseDate"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movies.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getFloat(2));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        System.out.println(movies);
        return new ArrayList<>();
    }

    public ArrayList<String> retrieveRows() { //query the database and return records as a text
        ArrayList<String> productRows = new ArrayList<>();
        String[] columns = new String[] {"name","director", "cast", "releaseDate"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getFloat(2));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        System.out.println("THIS IS PRODUCT ROW FROM DATABASE"+productRows);
        return productRows;
    }
}

