package com.example.buzzworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    private ImageView imageView;
    private CheckBox checkBox;
    private TextView titleText, descriptionText;
    public static final String EXTRA_DRINK_ID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        imageView = findViewById(R.id.drink_image);
        titleText = findViewById(R.id.titleText);
        descriptionText = findViewById(R.id.descriptionText);
        checkBox = findViewById(R.id.checkBox);

        Intent intent = getIntent();
        int drinkId = (Integer) intent.getExtras().get(EXTRA_DRINK_ID);

        SQLiteOpenHelper buzzWorldDatabaseHelper = new BuzzWorldDatabaseHelper(this);
        try{
            SQLiteDatabase db = buzzWorldDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK", new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVOURITE"}, "_id = ?", new String[] {Integer.toString(drinkId)}, null, null, null);
            if(cursor.moveToFirst()){
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int imageId = cursor.getInt(2);
                boolean isFavourite = (cursor.getInt(3) == 1);

                imageView.setImageResource(imageId);
                titleText.setText(name);
                descriptionText.setText(description);
                checkBox.setChecked(isFavourite);
            }
            cursor.close();
            db.close();
        }catch (SQLException e){
            Toast.makeText(this, "db error", Toast.LENGTH_SHORT).show();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = getIntent();
                int drinkId = (Integer) intent.getExtras().get(EXTRA_DRINK_ID);
                ContentValues drinkValue = new ContentValues();
                drinkValue.put("FAVOURITE", checkBox.isChecked());
                try {
                    SQLiteDatabase db = buzzWorldDatabaseHelper.getWritableDatabase();
                    db.update("DRINK",
                            drinkValue,
                            "_id = ?",
                            new String[] {Integer.toString(drinkId)});
                    db.close();
                }catch (SQLException e){
                    Toast.makeText(DrinkActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}