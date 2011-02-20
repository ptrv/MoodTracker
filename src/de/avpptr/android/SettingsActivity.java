package de.avpptr.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity{
	
	private MoodsDatabaseManager db;
	private int mNumberDbRows = 0;
	
	private TextView mStatusText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);
       
        db = new MoodsDatabaseManager(this);
        
        mStatusText = (TextView) findViewById(R.id.TextViewStatus);
        setStatusText();
    }
	@Override
	protected void onPause() {
		Log.d("MoodTracker", "SettingsActivity Pause");
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Log.d("MoodTracker", "SettingsActivity Resume");
		setStatusText();
		super.onResume();
	}

	private void setStatusText() {
    	mNumberDbRows = db.getRowCount();
    	mStatusText.setText(getString(R.string.settingsStatusText)+" "+mNumberDbRows);
	}

	public void settingsButtonsHandler(View view){
    	switch (view.getId()) {
    	case R.id.ButtonClearDb:
            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setMessage("Do you really want to delete all database entries?");
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	try
                	{
                		db.deleteAllRows();
                		Toast.makeText(getApplicationContext(), "Deleted "+mNumberDbRows+" database entries!", Toast.LENGTH_SHORT).show();
                	}
                	catch (Exception e)
                	{
                		Log.e("Clearing database Error", e.toString());
                		e.printStackTrace();
                	}
                	setStatusText();
                }
            });
            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(getApplicationContext(), "Deleting aborted", Toast.LENGTH_SHORT).show();
                }
            });
            alertbox.show();
    		break;
    	case R.id.ButtonSaveDbAsText:
    		saveFile(false);
    		break;
    	case R.id.ButtonSaveSqlFile:
//    		EditText editUser = (EditText) findViewById(R.id.EditTextUserName);
//			Editable userName = editUser.getText();
//			if(userName.length() != 0){
//	    		saveFile(true);
//			} else {
//	            AlertDialog.Builder useralertbox = new AlertDialog.Builder(this);
//	            useralertbox.setMessage("Please provide a user name!");
//	            useralertbox.setNeutralButton("Ok", null);
//	            useralertbox.show();
//			}
			saveFile(true);
    		break;
    	}
    }

	private void saveFile(boolean isSqlFile) {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    Toast.makeText(this, "External store not writable!", Toast.LENGTH_SHORT).show();
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
			Toast.makeText(this, "External storage not available!", Toast.LENGTH_SHORT);
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		if(mExternalStorageAvailable && mExternalStorageWriteable){
			File path = Environment.getExternalStorageDirectory();
    		
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    		String now = df.format(dt);
    		
    		File newPath = new File(path+"/moodtracker/");
    		File file = null;
    		ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
    		
    		
//			File file = new File(newPath, "moodtracker_"+now+".txt");
    		String str = "";
    		
			if (isSqlFile) {
				file = new File(newPath, "moodtracker_"+now+".sql");
				EditText editText = (EditText) findViewById(R.id.EditTextTableName);
				Editable tableName = editText.getText();
//				EditText editText2 = (EditText) findViewById(R.id.EditTextUserName);
//				Editable userName = editText2.getText();
				for (int position = 0; position < data.size(); position++) {
					ArrayList<Object> row = data.get(position);
					
					str += "INSERT INTO " + tableName.toString() + " (";
//					str += "user,";
					str += Moods.HAPPINESS + ",";
					str += Moods.TIREDNESS + ",";
					str += Moods.HOPEFUL + ",";
					str += Moods.STRESS + ",";
					str += Moods.SECURE + ",";
					str += Moods.ANXIETY + ",";
					str += Moods.PRODUCTIVE + ",";
					str += Moods.LOVED + ",";
					str += Moods.NOTE + ",";
					str += Moods.CREATED_DATE + ") VALUES (";
//					str += userName.toString() + ",";
					str += row.get(3).toString()+",";
					str += row.get(4).toString()+",";
					str += row.get(5).toString()+",";
					str += row.get(6).toString()+",";
					str += row.get(7).toString()+",";
					str += row.get(8).toString()+",";
					str += row.get(9).toString()+",";
					str += row.get(10).toString()+",";
					str += "\""+row.get(2).toString()+"\",";
					str += "\""+row.get(1).toString()+"\");";
					str += "\n";
							
				}
			} else {
				file = new File(newPath, "moodtracker_"+now+".txt");
				
				String header = "time,happiness,tiredness,hopeful,stress,secure,anxiety,productive,loved,message\n";
				str = header;
				for (int position = 0; position < data.size(); position++) {
					ArrayList<Object> row = data.get(position);
					
					str += "\""+row.get(1).toString()+"\",";
					str += row.get(3).toString()+",";
					str += row.get(4).toString()+",";
					str += row.get(5).toString()+",";
					str += row.get(6).toString()+",";
					str += row.get(7).toString()+",";
					str += row.get(8).toString()+",";
					str += row.get(9).toString()+",";
					str += row.get(10).toString()+",";
					str += "\""+row.get(2).toString()+"\"";
					str += "\n";
				}
			}
			
			try {
				newPath.mkdirs();
				
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				
				out.write(str);
				out.close();
				
				Toast.makeText(this, "Saved file: "+file.getName(), Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				Log.e("Writing file ERROR", e.toString());
				e.printStackTrace();
			}

			
		}
	}
	
	
    
}
