package de.avpptr.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MoodsDatabaseManager{

	// the Activity or Application that is creating an object from this class.
	Context context;
 
	// a reference to the database used by this application/object
	private SQLiteDatabase db;

	private static final String DATABASE_NAME = "moodtracker.db";
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "moods";
    private static final String DATABASE_TABLE_CREATE =
                "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                Moods.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                Moods.HAPPINESS + " INTEGER," +
                Moods.TIREDNESS + " INTEGER," +
                Moods.HOPEFUL + " INTEGER," +
                Moods.STRESS + " INTEGER," +
                Moods.SECURE + " INTEGER," +
                Moods.ANXIETY + " INTEGER," +
                Moods.PRODUCTIVE + " INTEGER," +
                Moods.LOVED + " INTEGER," +
                Moods.NOTE + " TEXT," +
                Moods.CREATED_DATE + " TEXT);";

    public MoodsDatabaseManager(Context context){
    	this.context = context;
    	
    	MoodsDatabaseHelper dbHelper = new MoodsDatabaseHelper(context);
    	this.db = dbHelper.getWritableDatabase();
    	
    }
    
    public void addRow(int val1, int val2, int val3, int val4, 
    		int val5, int val6, int val7, int val8, String note, String timeString){
    	// this is a key value pair holder used by android's SQLite functions
    	ContentValues values = new ContentValues();
     
    	
    	// this is how you add a value to a ContentValues object
    	// we are passing in a key string and a value string each time
    	values.put(Moods.HAPPINESS, val1);
    	values.put(Moods.TIREDNESS, val2);
    	values.put(Moods.HOPEFUL, val3);
    	values.put(Moods.STRESS, val4);
    	values.put(Moods.SECURE, val5);
    	values.put(Moods.ANXIETY, val6);
    	values.put(Moods.PRODUCTIVE, val7);
    	values.put(Moods.LOVED, val8);
    	values.put(Moods.NOTE, note);
    	values.put(Moods.CREATED_DATE, timeString);
    	
     
    	// ask the database object to insert the new data 
    	try
    	{
    		db.insert(DATABASE_TABLE_NAME, null, values);
    	}
    	catch(Exception e)
    	{
    		Log.e("DB ERROR", e.toString()); // prints the error message to the log
    		e.printStackTrace(); // prints the stack trace to the log
    	}	
    }

    public void deleteAllRows()
	{
		// ask the database manager to delete the row of given id
		try {db.execSQL("DELETE * FROM " + DATABASE_TABLE_NAME + ";");}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}

    private class MoodsDatabaseHelper extends SQLiteOpenHelper{
		public MoodsDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_TABLE_CREATE);
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
    };
    
    

}
