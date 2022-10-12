package com.example.buzzworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView, favouriteListView;
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        favouriteListView = findViewById(R.id.favouriteListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.category)
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,  DrinkCategoryActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        try{
            SQLiteOpenHelper buzzWorldDatabaseHelper = new BuzzWorldDatabaseHelper(this);
            db = buzzWorldDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                    new String[] {"_id", "NAME", "DESCRIPTION", "FAVOURITE"},
                    "FAVOURITE = 1",
                    null, null, null, null);
             adapter2 = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},
                    0);
            favouriteListView.setAdapter(adapter2);
        } catch (SQLException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newCursor = db.query("DRINK",
                new String[] {"_id", "NAME", "DESCRIPTION", "FAVOURITE"},
                "FAVOURITE = 1",
                null, null, null, null);
        CursorAdapter adapter3 = (CursorAdapter) favouriteListView.getAdapter();
        adapter3.changeCursor(newCursor);
        adapter2 = adapter3;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}