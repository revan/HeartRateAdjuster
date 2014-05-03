package com.example.heartrateadjuster;
/**
 * This StatisticsActivity is the entry point for the data management module, and it provides three basic functions with which other modules can interface.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class StatisticsActivity extends Activity {

	/**
	 * When the main activity is created, the database is cleared of all records.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
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
