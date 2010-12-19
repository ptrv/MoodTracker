package de.avpptr.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	protected void onResume() {
		setStatusText();
		super.onResume();
	}

	private void setStatusText() {
    	mNumberDbRows = db.getRowCount();
    	mStatusText.setText(getString(R.string.settingsStatusText)+mNumberDbRows);
	}

	public void settingsButtonsHandler(View view){
    	switch (view.getId()) {
    	case R.id.ButtonClearDb:
            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setMessage("You relly want to delete all database entries?");
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
    		break;
    	}
    }
    
}
