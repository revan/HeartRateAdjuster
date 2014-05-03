package com.example.datamanagement;

/** 
 * The StoreActivity runs the testing script for storing the records.
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class StoreActivity extends Activity {

	/**
	 * The onCreate function is called when the user presses the Store button; it begins the storage of the records
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		Intent storeIntent = getIntent(); // get the intent from the store function
		
		DataAssembler db = new DataAssembler(this); // instantiate a database object and open the connection
		
		
		Log.d("Insert: ", "Inserting Records into the Database...");
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),1235,"Eminem","Rap","Till I Collapse")); // load some sample records into the database
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),2235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),3235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),4235,"Eminem","Rap","Till I Collapse"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),5235,"Eminem","Rap","Till I Collapse"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),6235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),7235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),8235,"Eminem","Rap","Till I Collapse"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),9235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),10235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),11235,"Eminem","Rap","Till I Collapse"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),12235,"Bono","Pop","Beautiful Day"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),13236,"Beatles","Rock","Ticket to Ride"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),14236,"K'naan","Cultural","Wavin Flag"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),15236,"K'naan","Cultural","Wavin Flag"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),16236,"Beatles","Rock","Ticket to Ride"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),17236,"K'naan","Cultural","Wavin Flag"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),18236,"K'naan","Cultural","Wavin Flag"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),19236,"Beatles","Rock","Ticket to Ride"));
		db.addRecord(new Record(60+(int)(Math.random() *((80-60)+1)),20236,"K'naan","Cultural","Wavin Flag"));
		
		
		setContentView(R.layout.activity_store);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store, menu);
		return true;
	}

}
