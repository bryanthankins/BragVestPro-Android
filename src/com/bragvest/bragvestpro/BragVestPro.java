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
import android.widget.ViewFlipper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleCursorAdapter;

public class BragVestPro extends Activity {
    /** Called when the activity is first created. */
    private static String[] FROM = { "name", "_id" };
    private static int[] TO = { R.id.name_entry};
    private static String ORDER_BY = "_id DESC";
    private static String TABLE_NAME = "ranks";
    private DataBaseHelper dbHelper;
    private ViewFlipper flipper;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
          
            //Set initial view
            setContentView(R.layout.main);
            
            // Get data from database
            SQLiteDatabase db;
            dbHelper = new DataBaseHelper(this);
            dbHelper.createDataBase();
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
            ListView ranks = (ListView)findViewById(R.id.ListView01);
            ranks.setAdapter(adapter);


            
            

            // Handle click event
            ranks.setOnItemClickListener(new ListView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                	//TODO: load up second list view with badge data based on selection
                    flipper.showNext();
                }
            });

        }
   /* @Override
        public void onClick(View v) {
                flipper.showNext();
        }*/
}
