package com.example.heartrateadjuster;
/**
 * This Activity runs tests to make sure that retrieval of data works properly.
 */
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/**
 * This RetrieveActivity is a script that runs to extract all the Record objects from the database.
 */
public class RetrieveActivity extends Activity {
	/**
	 * This RetrieveActivity is a script that runs to extract all the Record objects from the database.
	 */
	
	
	/**
	 * The onCreate function is called when the user presses the Retrieve button; it begins the retrieval of the records
	 * @param savedInstanceState 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent storeIntent = getIntent(); // gets the intent from the store function
		DataAssembler db = new DataAssembler(this); // instantiates a database object and opens the connection
		
		List<Record> temp = db.getAllRecords(); // retrieves all the records from the database
		
		for(Record record : temp) { // prints all the parameters of each record to the console.
			System.out.println("Heart Rate: " + record.getHeartRate() + 
							   " Time Stamp: " + record.getTimeStamp() + 
							   " Song: " 	  + record.getSong() 	  + 	
							   " Artist: " 	  + record.getArtist()    +
							   " Genre: " 	  + record.getGenre() 	  );
		}
		
		
		setContentView(R.layout.activity_retrieve);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.retrieve, menu);
		return true;
	}

}
