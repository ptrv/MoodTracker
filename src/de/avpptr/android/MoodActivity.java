package de.avpptr.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

public class MoodActivity extends Activity {
	
	private static int NUM_MOODS = 8;
	private int[] mMoodValues = new int[NUM_MOODS];
	private TextView[] mMoodLabels = new TextView[NUM_MOODS];
	private String[] mMoodNames = new String[NUM_MOODS];
	private SeekBar[] mMoodSliders = new SeekBar[NUM_MOODS];
//	private Editable mMoodNote;
	private EditText mNoteField;
	private Button mButtonSubmit;
	private boolean mMoodNoteFirsFocus = true;
	
	private PopupWindow mPopup;
	//how much time your popup window should appear
	private static final int POPUP_DISMISS_DELAY = 1000;
	private DismissPopup mDismissPopup = new DismissPopup();
	class DismissPopup implements Runnable {
        public void run() {
            // Protect against null-pointer exceptions
            if (mPopup != null) {
                mPopup.dismiss();
            }
        }
    }
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
        
        LayoutInflater inflater = (LayoutInflater) MoodActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopup = new PopupWindow(inflater.inflate(R.layout.popup_submit,null, false),200,50,true);   
        mPopup.setOutsideTouchable(false);
        mPopup.setTouchInterceptor(new OnTouchListener() {

        	public boolean onTouch(View v, MotionEvent event) {
        		//your code when touched on the event
        		return false;
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
			resetMood();
//			LayoutInflater inflater = (LayoutInflater) MoodActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			//Here x is the name of the xml which contains the popup components
//			PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup_submit,null, false),300,400,true);
//			//Here y is the id of the root component
//
//			pw.showAtLocation(findViewById(R.id.ButtonSubmit), Gravity.CENTER, 0,0);
			//mNoteField.setText("button clicked");
			
			mPopup.showAtLocation(this.findViewById(R.id.LinearLayoutMaster),Gravity.CENTER, 0, 0);
		    View v = findViewById(R.id.LinearLayoutMaster);
			v.postDelayed(mDismissPopup, POPUP_DISMISS_DELAY);
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