package de.avpptr.android;

import de.avpptr.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class TodayActivity extends Activity {
	
	private static int NUM_MOODS = 8;
	private int[] mMoodValues = new int[NUM_MOODS];
	private TextView[] mMoodLabels = new TextView[NUM_MOODS];
	private String[] mMoodNames = new String[NUM_MOODS];
	private SeekBar[] mMoodSliders = new SeekBar[NUM_MOODS];
	private Editable mMoodNote;
	private EditText mNoteField;
	private Button mButtonSubmit;
	private boolean mMoodNoteFirsFocus = true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.today_layout2);
        
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
//        mNoteField.setOnKeyListener(new OnKeyListener() {
//			
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// If the event is a key-down event on the "enter" button
//				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//						(keyCode == KeyEvent.KEYCODE_ENTER)) {
//					mMoodNote = mNoteField.getText();
//					return true;
//				}
//				return false;
//			}
//		});
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
        
//        setKeyListener(new KeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                  // Perform action on key press
//                  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
//                  return true;
//                }
//                return false;
//            }
//        });
        mButtonSubmit = (Button) findViewById(R.id.ButtonSubmit);
    }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
    
	public void submitButtonHandler(View view){
		switch (view.getId()) {
		case R.id.ButtonSubmit:
			saveMood();
			resetMood();
			break;
		default:
			break;
		}		
	}
	
	private void saveMood() {
		// TODO Auto-generated method stub
		
	}
	private void resetMood() {
		// TODO Auto-generated method stub
		
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