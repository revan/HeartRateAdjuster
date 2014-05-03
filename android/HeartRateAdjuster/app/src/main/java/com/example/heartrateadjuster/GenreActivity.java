package com.example.heartrateadjuster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GenreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genre);
		
		Intent pieIntent = getIntent();
		
		System.out.println("the Song button was pressed");
		
		/*
		 * Start the logic
		 * 1) Create a Hash Set
		 * 2) For each Record in the database, try and add the Artist to the Hash Set
		 * 3) Convert the Hash Set into an Array
		 * 4) Use the array to find out the frequencies
		 * 5) Get the code from the other place and create the chart.
		 */
		CreateGenrePie();
	}

	
	private void CreateGenrePie() {
		DataAssembler db = new DataAssembler(this);
		
		HashSet genres = new HashSet();
		List<Record> temp = db.getAllRecords();
		ArrayList<String> myGenres = new ArrayList<String>(); // has all genres 
		
		for(Record record : temp)
		{
				genres.add(record.getGenre());
				myGenres.add(record.getGenre());
		}
		// Convert to an array. Has unique genres
		String[] uniqueGenres = (String[]) genres.toArray(new String[genres.size()]);
		
		double[] frequencies = new double[uniqueGenres.length];
		
		for(int i = 0; i < uniqueGenres.length; i++)
		{
			//System.out.println(uniqueGenres[i]);
			int count = Collections.frequency(myGenres,uniqueGenres[i]);
			//System.out.println("Count is " + count);
			double percentage = (count / ((double)temp.size())) * 100;
			//System.out.println("Percentage is " + percentage);
			frequencies[i] = percentage;
			
		}
		
		int[] colors = {Color.BLUE,Color.GREEN};
		CategorySeries distributionSeries = new CategorySeries("Frequencies of Genres");
		for(int i = 0; i < frequencies.length;i++)
		{
			distributionSeries.add(uniqueGenres[i],frequencies[i]);
		}
		
		
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for(int i = 0; i < frequencies.length; i++)
		{
			SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
			seriesRenderer.setColor(colors[i%2]);
			seriesRenderer.setDisplayChartValues(true);
			defaultRenderer.addSeriesRenderer(seriesRenderer);
		}
		
		defaultRenderer.setLegendTextSize(30);
		defaultRenderer.setChartTitle("Frequencies of Genres");
		defaultRenderer.setChartTitleTextSize(50);
		defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setApplyBackgroundColor(true);
		defaultRenderer.setBackgroundColor(Color.BLACK);
		
		Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "PieChart");
		
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.genre, menu);
		return true;
	}

}
