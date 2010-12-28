package de.avpptr.android;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    MoodsDatabaseHelper dbHelper;
    
    public MoodsDatabaseManager(Context context){
    	this.context = context;
    	
    	dbHelper = new MoodsDatabaseHelper(context);
//    	this.db = dbHelper.getWritableDatabase();
    	
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
    	this.db = dbHelper.getWritableDatabase();
    	try
    	{
    		db.insert(DATABASE_TABLE_NAME, null, values);
    	}
    	catch(Exception e)
    	{
    		Log.e("DB ERROR", e.toString()); // prints the error message to the log
    		e.printStackTrace(); // prints the stack trace to the log
    	}
    	db.close();
//    	db.close();
    }

    public void deleteAllRows()
	{
    	this.db = dbHelper.getWritableDatabase();
		// ask the database manager to delete the row of given id
		try {
			db.execSQL("DELETE FROM " + DATABASE_TABLE_NAME + ";");
		}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		try {
			db.execSQL("DELETE FROM sqlite_sequence WHERE name='"+ DATABASE_TABLE_NAME + "';");
		}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		db.close();

	}

    public int getRowCount() {
    	this.db = dbHelper.getReadableDatabase();
    	Cursor cursor;
		int count = 0;
		try {
			cursor = db.rawQuery("SELECT * FROM moods;", null);
			count = cursor.getCount();
			cursor.close();
		}
		catch (Exception e)
		{
			Log.e("Counting rows ERROR", e.toString());
			e.printStackTrace();
		}
		db.close();
		return count;
	};

	public ArrayList<ArrayList<Object>> getAllRowsAsArrays()
	{
		this.db = dbHelper.getReadableDatabase();
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		db.beginTransaction();
		Cursor cursor = null;
 
		try
		{
			// ask the database object to create the cursor.
			cursor = db.query(
					DATABASE_TABLE_NAME,
					new String[]{Moods.ID, 
							Moods.CREATED_DATE, 
							Moods.NOTE,
							Moods.HAPPINESS,
							Moods.TIREDNESS,
							Moods.HOPEFUL,
							Moods.STRESS,
							Moods.SECURE,
							Moods.ANXIETY,
							Moods.PRODUCTIVE,
							Moods.LOVED},
					null, null, null, null, null
			);
 
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast()){
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getString(5));
					dataList.add(cursor.getString(6));
					dataList.add(cursor.getString(7));
					dataList.add(cursor.getString(8));
					dataList.add(cursor.getString(9));
					dataList.add(cursor.getString(10));
 
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
			db.setTransactionSuccessful();
			
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
		}
		db.close();
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
	public ArrayList<ArrayList<Object>> getRowsAsArraysForLog()
	{
		this.db = dbHelper.getReadableDatabase();
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		db.beginTransaction();
		Cursor cursor = null;
 
		try
		{
			// ask the database object to create the cursor.
			cursor = db.query(
					DATABASE_TABLE_NAME,
					new String[]{Moods.ID, Moods.CREATED_DATE, Moods.NOTE},
					null, null, null, null, null
			);
 
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
 
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
			db.setTransactionSuccessful();
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}finally{
			db.endTransaction();
			cursor.close();
		}
		
		db.close();
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}

	public void deleteRow(long rowID)
	{
		this.db = dbHelper.getWritableDatabase();
		// ask the database manager to delete the row of given id
		try {db.delete(DATABASE_TABLE_NAME, Moods.ID + "=" + rowID, null);}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		db.close();
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
			
		}
    }

}
