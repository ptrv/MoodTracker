package de.avpptr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TodayActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.today_layout);
    }
}