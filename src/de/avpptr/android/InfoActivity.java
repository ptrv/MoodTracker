package de.avpptr.android;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		
		PackageManager pm = getPackageManager();
		try {
            //---get the package info---
            PackageInfo pi = pm.getPackageInfo("de.avpptr.android", 0);
            //---display the versioncode---
            TextView versionView = (TextView) findViewById(R.id.TextViewVersionNum);        
            versionView.setText("Version: "+Integer.toString(pi.versionCode));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
	}
	protected void onPause() {
		Log.d("MoodTracker", "InfoActivity Pause");
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Log.d("MoodTracker", "InfoActivity Resume");
		super.onResume();
	}

}
