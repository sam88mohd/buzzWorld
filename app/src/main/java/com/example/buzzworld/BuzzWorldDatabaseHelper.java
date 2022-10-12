package com.example.buzzworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BuzzWorldDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "buzzWorld";
    private static final int DB_VERSION = 2;

    public BuzzWorldDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, 1, 2);
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues drinkValue = new ContentValues();
        drinkValue.put("NAME", name);
        drinkValue.put("DESCRIPTION", description);
        drinkValue.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValue);
    }

    private void updateMyDatabase(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        if(oldVersion < 1){
            sqLiteDatabase.execSQL("CREATE TABLE DRINK ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(sqLiteDatabase, "Latte", "Hot latte for you", R.drawable.latte_img);
            insertDrink(sqLiteDatabase, "Cappuccino", "Hot cappuccino for you", R.drawable.capucinno_img);
        }
        if(oldVersion < 2){
            sqLiteDatabase.execSQL("ALTER TABLE DRINK ADD COLUMN FAVOURITE NUMERIC");
        }
    }
}
