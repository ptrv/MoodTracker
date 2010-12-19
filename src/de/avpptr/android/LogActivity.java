package de.avpptr.android;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LogActivity extends Activity{
	
	TableLayout dataTable;
	
//	private Vector<String> mMoodsList;
//	private ListView mListView;
//	
//	private String[] mListStrings = {"No content"};
	
	private MoodsDatabaseManager db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_layout);
        
        dataTable = (TableLayout)findViewById(R.id.data_table);
//        mListView = (ListView) findViewById(R.id.ListViewMoods);
//        
        db = new MoodsDatabaseManager(this);
//        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.log_list_item, mListStrings));
//        setContent();

        updateTable();
    }

    
    @Override
	protected void onResume() {
    	updateTable();
		super.onResume();
	}


	private void updateTable()
    {
    	// delete all but the first row.  remember that the count 
    	// starts at one and the index starts at zero
    	while (dataTable.getChildCount() > 1)
    	{
    		// while there are at least two rows in the table widget, delete
    		// the second row.
    		dataTable.removeViewAt(1);
    	}
 
    	// collect the current row information from the database and
    	// store it in a two dimensional ArrayList
    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
 
    	// iterate the ArrayList, create new rows each time and add them
    	// to the table widget.
    	for (int position=0; position < data.size(); position++)
    	{
    		TableRow tableRow= new TableRow(this);
 
    		ArrayList<Object> row = data.get(position);
 
    		TextView idText = new TextView(this);
    		idText.setText(row.get(0).toString());
    		tableRow.addView(idText);
 
    		TextView textOne = new TextView(this);
    		textOne.setText(row.get(1).toString());
    		tableRow.addView(textOne);
 
//    		TextView textTwo = new TextView(this);
//    		textTwo.setText(row.get(2).toString());
//    		tableRow.addView(textTwo);
 
    		dataTable.addView(tableRow);
    	}
    }

//    private void setContent() {
//
//    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
//
//    	mMoodsList.clear();
//    	for (int position=0; position < data.size(); position++)
//    	{ 
//    		ArrayList<Object> row = data.get(position);
//
//    		String listItem = row.get(0).toString()+"|"+row.get(1).toString()+"|"+row.get(2);
//
//    		mMoodsList.add(listItem);
//    	}
//
//    	if(mMoodsList.size() == 0)
//    	{
//    		mMoodsList.add("No content");
//    	}
//    	String[] listStrings = new String[mMoodsList.size()];
//    	for(int i = 0; i < mMoodsList.size(); i++){
//    		listStrings[i] = mMoodsList.get(i);
//    		Log.d("String list item", listStrings[i]);
//
//    	}
//    	//        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.log_list_item, listStrings));
//    }
}
