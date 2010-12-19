package de.avpptr.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MoodActivity extends Activity {
	
	private static int NUM_MOODS = 8;
	private int[] mMoodValues = new int[NUM_MOODS];
	private TextView[] mMoodLabels = new TextView[NUM_MOODS];
	private String[] mMoodNames = new String[NUM_MOODS];
	private SeekBar[] mMoodSliders = new SeekBar[NUM_MOODS];
//	private Editable mMoodNote;
	private EditText mNoteField;
	private boolean mMoodNoteFirsFocus = true;
	
	private static final int SHOW_TOAST_LENGTH = 1000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mood_layout);
        
        mMoodSliders[0] = (SeekBar) findViewById(R.id.SeekBarHappiness);
        mMoodLabels[0] = (TextView) findViewById(R.id.TextViewHappiness);
        mMoodSliders[1] = (SeekBar) findViewById(R.id.SeekBarTiredness);
        mMoodLabels[1] = (TextView) findViewById(R.id.TextViewTiredness);
        mMoodSliders[2] = (SeekBar) findViewById(R.id.SeekBarHopeful);
        mMoodLabels[2] = (TextView) findViewById(R.id.TextViewHopeful);
        mMoodSliders[3] = (SeekBar) findViewById(R.id.SeekBarStress);
        mMoodLabels[3] = (TextView) findViewById(R.id.TextViewStress);
        mMoodSliders[4] = (SeekBar) findViewById(R.id.SeekBarSecure);
        mMoodLabels[4] = (TextView) findViewById(R.id.TextViewSecure);
        mMoodSliders[5] = (SeekBar) findViewById(R.id.SeekBarAnxiety);
        mMoodLabels[5] = (TextView) findViewById(R.id.TextViewAnxiety);
        mMoodSliders[6] = (SeekBar) findViewById(R.id.SeekBarProductive);
        mMoodLabels[6] = (TextView) findViewById(R.id.TextViewProductive);
        mMoodSliders[7] = (SeekBar) findViewById(R.id.SeekBarLoved);
        mMoodLabels[7] = (TextView) findViewById(R.id.TextViewLoved);

        for(int i = 0; i < NUM_MOODS; i++){
        	mMoodValues[i] = 0;
            mMoodSliders[i].setOnSeekBarChangeListener(seekBarChangeListener);
            mMoodNames[i] = (String) mMoodLabels[i].getText();

        }

        mNoteField = (EditText) findViewById(R.id.NoteField);

        mNoteField.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(v == mNoteField && mMoodNoteFirsFocus && hasFocus){
					mNoteField.setText("");
					mMoodNoteFirsFocus = true;
				}
//				else if(v == mNoteField && !hasFocus && (mMoodNote.length() == 0)){
//					mNoteField.setText(R.string.noteFieldDefault);
//				}
			}
		});
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
    
	public void buttonSubmitHandler(View view){
		switch (view.getId()) {
		case R.id.ButtonSubmit:
			saveMood();
			Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		              resetMood(); 
		         } 
		    }, SHOW_TOAST_LENGTH);
		    Toast.makeText(this, R.string.toastSubmitText, SHOW_TOAST_LENGTH).show();
			break;
		default:
			break;
		}		
	}
	
	private void saveMood() {
		// TODO Auto-generated method stub
		
	}
	private void resetMood() {
		for(int i = 0; i < NUM_MOODS; i++){
			mMoodSliders[i].setProgress(0);
			mMoodLabels[i].setText(mMoodNames[i]);
		}
		mNoteField.setText(getString(R.string.noteFieldDefault));
	}

	private SeekBar.OnSeekBarChangeListener seekBarChangeListener
	= new SeekBar.OnSeekBarChangeListener()
	{

		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			for(int i = 0; i < NUM_MOODS; i++){
				if(seekBar == mMoodSliders[i])
				{
					mMoodValues[i] = progress;
					mMoodLabels[i].setText(mMoodNames[i] + ": " + progress);
					break;
				}
			}
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
	};
}