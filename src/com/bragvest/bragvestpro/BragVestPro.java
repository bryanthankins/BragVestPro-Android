package com.bragvest.bragvestpro;

import java.io.IOException;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class BragVestPro extends ListActivity {
    /** Called when the activity is first created. */
    private static String[] FROM = { "name", "_id" };
    private static int[] TO = { R.id.name_entry};
    private static String ORDER_BY = "_id DESC";
    private static String TABLE_NAME = "ranks";
    private DataBaseHelper dbHelper;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Get data from database
            SQLiteDatabase db;
            dbHelper = new DataBaseHelper(this);
          //  try {
                dbHelper.createDataBase();
          //  } catch (IOException ioe) {
          //      throw new Error("Unable to create database");
         //   }
            try {
                db = dbHelper.openDataBase();
            }catch(SQLException sqle){
                throw sqle;
            }

            Cursor cursor = db.query(TABLE_NAME, FROM, null, null,null,
                    null,null);
            startManagingCursor(cursor);
            
            // Tie to UI
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.list_item, cursor, FROM, TO);
            setListAdapter(adapter);
            ListView lv = getListView();
            lv.setTextFilterEnabled(true);

            // Handle click event
            lv.setOnItemClickListener(new ListView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                    // When clicked, show a toast with the TextView text
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();
                }
            });
        }
}
