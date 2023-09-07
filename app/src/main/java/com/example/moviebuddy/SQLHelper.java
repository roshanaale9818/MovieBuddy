package com.example.moviebuddy;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.moviebuddy.DatabaseManager;

public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context c) {
        super(c, DatabaseManager.DB_NAME, null, DatabaseManager.DB_VERSION);
//        System.out.println("****** CREATING DATABASE******");
//        SQLiteDatabase db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("*** CREATING TABLE***");
//        db.execSQL(DatabaseManager.CREATE_TABLE);
        try {
//            Log.d(LOG_TAG, "A table is created with this SQL-String: " + SQL_CREATE + " angelegt.");
            db.execSQL(DatabaseManager.CREATE_TABLE);
            db.execSQL(DatabaseManager.CREATE_CINEMA);
        }
        catch (Exception ex) {
           System.out.println(ex.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("*****ONUPGRADE IS CALLED ******");
        Log.w("Products table", "Upgrading database i.e. dropping table and recreating it");
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.DB_CINEMA_TABLE);
        onCreate(db);
    }
}

