package de.avpptr.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LogListActivity extends ListActivity {

	private SimpleAdapter mMoodsList;
	
	private MoodsDatabaseManager db;
	
	ArrayList<HashMap<String, String>> mylist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mylist = new ArrayList<HashMap<String, String>>();
		db = new MoodsDatabaseManager(this);
		
		setContent();
		
		mMoodsList = new SimpleAdapter(this, mylist, R.layout.log_row,
		            new String[] {Moods.ID, Moods.CREATED_DATE}, new int[] {R.id.ID_CELL, R.id.TIME_CELL});
		setListAdapter(mMoodsList);
	}
	@Override
	protected void onPause() {
		Log.d("LogListActivity", "Pause");
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Log.d("LogListActivity", "Resume");
		setContent();
		super.onResume();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		AlertDialog.Builder adb = new AlertDialog.Builder(l.getContext());
		adb.setTitle("Mood Record");
		adb.setMessage("Created: "+mylist.get(position).get(Moods.CREATED_DATE)+"\n"
				+ "Happiness: "+mylist.get(position).get(Moods.HAPPINESS)+"\n"
				+ "Tiredness: "+mylist.get(position).get(Moods.TIREDNESS)+"\n"
				+ "Hopeful: "+mylist.get(position).get(Moods.HOPEFUL)+"\n"
				+ "Stress: "+mylist.get(position).get(Moods.STRESS)+"\n"
				+ "Secure: "+mylist.get(position).get(Moods.SECURE)+"\n"
				+ "Anxiety: "+mylist.get(position).get(Moods.ANXIETY)+"\n"
				+ "Productive: "+mylist.get(position).get(Moods.PRODUCTIVE)+"\n"
				+ "Loved: "+mylist.get(position).get(Moods.LOVED)+"\n"
				+ "Note: "+mylist.get(position).get(Moods.NOTE)+"\n");

		adb.setPositiveButton("Ok", null);
		adb.show();

	}

	private void setContent() {
		mylist.clear();
		
    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
    	 
    	// iterate the ArrayList, create new rows each time and add them
    	// to the table widget.
    	for (int position=0; position < data.size(); position++)
    	{
    		ArrayList<Object> row = data.get(position);
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put(Moods.ID, row.get(0).toString());
    		map.put(Moods.CREATED_DATE, row.get(1).toString());
    		map.put(Moods.NOTE, row.get(2).toString());
    		map.put(Moods.HAPPINESS, row.get(3).toString());
    		map.put(Moods.TIREDNESS, row.get(4).toString());
    		map.put(Moods.HOPEFUL, row.get(5).toString());
    		map.put(Moods.STRESS, row.get(6).toString());
    		map.put(Moods.SECURE, row.get(7).toString());
    		map.put(Moods.ANXIETY, row.get(8).toString());
    		map.put(Moods.PRODUCTIVE, row.get(9).toString());
    		map.put(Moods.LOVED, row.get(10).toString());
    		
    		mylist.add(map);
    	}
	}
}
