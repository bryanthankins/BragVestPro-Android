package com.bragvest.bragvestpro;

import java.io.IOException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleCursorAdapter;

public class BragVestPro extends ListActivity {
    private static String[] FROM_FIELDS = { "name", "_id" };
    private static int[] TO = { R.id.name_entry};
    private static String ORDER_BY = "_id DESC";
    private DataBaseHelper dbHelper;
    private ViewFlipper flipper;
    private SQLiteDatabase db;
    private String currLevel = "";
    private String prevLevel = "";
    private static int currLevelCounter = 1; //start on level 1
    private static long filterID = 0; 
    public enum Level { ranks, badges, achievements, tasks }


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            // Get friendly name of level 
            currLevel = getLevelNameFromNumber(currLevelCounter, false);
            
            // Get data from database
            dbHelper = new DataBaseHelper(this);
            dbHelper.createDataBase();
            try {
                db = dbHelper.openDataBase();
            }catch(SQLException sqle){
                throw sqle;
            }

            // Only apply filter if one exists            
            String filter = null;
            if(currLevelCounter > 1)
            {
            	filter = getLevelNameFromNumber(currLevelCounter - 1,true) + "_id = " + Long.toString(filterID);
            	
            }
            Cursor cursor = db.query(currLevel, FROM_FIELDS, filter, null,null,
                    null,null);
            startManagingCursor(cursor);
            
            
            // Tie to UI
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.list_item, cursor, FROM_FIELDS, TO);
            setListAdapter(adapter);
            
        }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        // hook the back button to keep track of where we're at
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            currLevelCounter--;
        }

        return super.onKeyDown(keyCode, event);
    }
    
    private String getLevelNameFromNumber(int number, boolean singular)
    {
    	String answer = null;
        if(number== 1)
          {
              answer = "ranks";
          }
        else if(number == 2)
        {
        	answer = "badges";
        }
        else if(number == 3)
        {
        	answer = "achievements";
        }
        else if (number == 4)
        {
        	answer = "tasks";        
        }
    	if(singular)
    	{
            // change 'tasks' to 'task'
    		answer = answer.substring(0, answer.length() - 1);
    	}
    	return answer;
          
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
        // keep track of level and selected item
    	currLevelCounter++;
    	filterID = id;
    	Intent drilldown = new Intent(this, BragVestPro.class);
  	  	this.startActivity(drilldown); 
        
        //TODO: launch detail activity if at level 4

    }
}
