package de.avpptr.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends Activity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);
    }
    
    public void settingsButtonsHandler(View view){
    	switch (view.getId()) {
    	case R.id.ButtonClearDb:
    		break;
    	case R.id.ButtonSaveDbAsText:
    		break;
    	}
    }
    
}
