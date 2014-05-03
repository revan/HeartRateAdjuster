package com.example.datamanagement;
/**
 * This MainActivity is the entry point for the data management module, and it provides three basic functions with which other modules can interface.
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import java.util.List;
import android.util.Log;

public class MainActivity extends Activity {

	/**
	 * When the main activity is created, the database is cleared of all records.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		DataAssembler db = new DataAssembler(this); // creates a database object
		List<Record> recordList = db.getAllRecords(); // retrieves all the records that are currently in the database
		
		for(Record record : recordList) { // deletes all the records from the database
			db.deleteRecord(record);
		}
	}

	
	
	/**
	 * The Retrieve function is called when the Retrieve button is pressed; it initiates the activity which retrieves the data.
	 * @param view The function takes the current view as a parameter since the initiator is a button press.
	 */
	public void retrieve(View view) {
		Intent intent = new Intent(this,RetrieveActivity.class);
		startActivity(intent);
	}
	
	/**
	 * The store function is called when the Store button is pressed; it initiates the activity which stores the data.
	 * @param view The button press from the current view is used to initiate this function.
	 */
	public void store(View view) {
		Intent storeIntent = new Intent(this,StoreActivity.class);
		startActivity(storeIntent);
	}
	
	/**
	 * The graphData function is called when the Graph button is pressed; it initiates the activity which graphs the data
	 * @param view The function takes the current view as a parameter since the initiator is a button press.
	 */
	public void graphData(View view) {
		Intent graphIntent = new Intent(this,LineActivity.class);
		startActivity(graphIntent);
	}
	
		
	public void graphArtist(View view) {
		Intent artistIntent = new Intent(this,ArtistActivity.class);
		startActivity(artistIntent);
	}
	
	public void graphSong(View view) {
		Intent songIntent = new Intent(this,SongActivity.class);
		startActivity(songIntent);
	}
	
	public void graphGenre(View view) {
		Intent genreIntent = new Intent(this,GenreActivity.class);
		startActivity(genreIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
