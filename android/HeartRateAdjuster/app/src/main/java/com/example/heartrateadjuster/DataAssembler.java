package com.example.heartrateadjuster;
/**
 * This Data Assembler class is responsible for creating the SQLite structure, as as providing functions to modify, add, or remove Records from the database.
 * @author Nikhil
 */


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * This Data Assembler class is responsible for creating the SQLite structure, as as providing functions to modify, add, or remove Records from the database.
*/

public class DataAssembler extends SQLiteOpenHelper {


		// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	//Database Name
	private static final String DATABASE_NAME = "heartRateMonitor";

	// Records table name
	private static final String TABLE_RECORDS = "records";

	//Records Table columns names
	private static final String KEY_HEARTRATE = "heart_rate";
	private static final String KEY_TIMESTAMP = "time_stamp";
	private static final String KEY_ARTIST = "artist";
	private static final String KEY_GENRE = "genre";
	private static final String KEY_SONG = "song";


	public DataAssembler(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	/**
	 * This method sets up SQL Database with the arrangement of its parameters
	 * @param db This is the SQLite database to be loaded.
	 */
	public void onCreate(SQLiteDatabase db) {
		String CREATE_RECORDS_TABLE = "CREATE TABLE " + TABLE_RECORDS + " ("
				+ KEY_HEARTRATE + " INTEGER," + KEY_TIMESTAMP + " INTEGER PRIMARY KEY,"
				+ KEY_ARTIST + " TEXT," + KEY_GENRE + " TEXT," + KEY_SONG + " TEXT" + ")";

		db.execSQL(CREATE_RECORDS_TABLE);
	}

	// Upgrading the database
	/**
	 * This function will upgrade the SQLite database if a new version is released
	 * @param The parameters are the SQLite database object, and the integers denoting the old and new versions
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);

		// Create tables again
		onCreate(db);
	}


	/**
	 * This function adds a record to the database
	 * @param record This record contains information about the heart rate, time stamp, artist, genre, and song
	 */
	void addRecord(Record record) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_HEARTRATE, record.getHeartRate()); // Record heart Rate
		values.put(KEY_TIMESTAMP, record.getTimeStamp()); // Record time stamp
		values.put(KEY_ARTIST, record.getArtist()); // Record heart Rate
		values.put(KEY_GENRE, record.getGenre()); // Record time stamp
		values.put(KEY_SONG, record.getSong()); // Record time stamp

		// Inserting Row
		db.insert(TABLE_RECORDS, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * This method retrieves a record from the database based on its time stamp
	 * @param timeStamp The time stamp is primary key used to fetch the record
	 * @return A Record object is returned
	 */
	// Getting single contact
	Record getRecord(int timeStamp) {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_RECORDS, new String[] { KEY_HEARTRATE,
					KEY_TIMESTAMP, KEY_ARTIST,KEY_GENRE,KEY_SONG }, KEY_TIMESTAMP + "=?",
					new String[] { String.valueOf(timeStamp) }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();

			Record record = new Record(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
					cursor.getString(2), cursor.getString(3),cursor.getString(4));
			// return record
			return record;
		}


	/**
	 * This function returns a list of all the records in the database
	 * @return A list containing all the Record objects in the database is returned
	 */
	// Getting All Contacts
		public List<Record> getAllRecords() {
			List<Record> recordList = new ArrayList<Record>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Record record = new Record();
					record.setHeartRate(Integer.parseInt(cursor.getString(0)));
					record.setTimeStamp(Integer.parseInt(cursor.getString(1)));
					record.setArtist(cursor.getString(2));
					record.setGenre(cursor.getString(3));
					record.setSong(cursor.getString(4));
					// Adding contact to list
					recordList.add(record);
				} while (cursor.moveToNext());
			}

			// return contact list
			return recordList;
		}

		/**
		 * This function allows a Record to be updated with new information.
		 * @param record The parameter is the record object to be updated.
		 * @return Returns a 1 if successful
		 */
		// Updating single contact
		public int updateRecord(Record record) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_HEARTRATE, record.getHeartRate());
			values.put(KEY_TIMESTAMP, record.getTimeStamp());
			values.put(KEY_ARTIST, record.getArtist());
			values.put(KEY_GENRE, record.getGenre());
			values.put(KEY_SONG, record.getSong());

			// updating row
			return db.update(TABLE_RECORDS, values, KEY_TIMESTAMP + " = ?",
					new String[] { String.valueOf(record.getTimeStamp()) });
		}

		/**
		 * This function deletes a record from the database.
		 * @param record The function takes the record to be deleted as an argument and returns nothing
		 */
		// Deleting single record
		public void deleteRecord(Record record) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_RECORDS, KEY_TIMESTAMP + " = ?",
					new String[] { String.valueOf(record.getTimeStamp()) });
			db.close();
		}

		/**
		 * This function the total number of Record objects in the database
		 * @return An integer containing the number of Records is returned.
		 */
		// Getting records Count
		public int getRecordsCount() {
			String countQuery = "SELECT  * FROM " + TABLE_RECORDS;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			cursor.close();

			// return count
			return cursor.getCount();
		}



}
