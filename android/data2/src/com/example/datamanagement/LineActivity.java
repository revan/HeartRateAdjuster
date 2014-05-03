package com.example.datamanagement;
/**
 * This Android activity essentially is a script that runs the graphing of the data.
 */
import java.io.File;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.ChartFactory;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;

/**
 * The LineActivity runs the script to test the graphing when the Graph button is pressed.
 * @author nikhil
 *
 */
public class LineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_data);
		
		Intent graphIntent = getIntent(); // retrieve the intent from the MainActivity
		
		System.out.println("the Graph Data button was pressed");
		
		DataAssembler db = new DataAssembler(this);
				
		List<Record> temp = db.getAllRecords();
				
		Record first = temp.get(0);
		int startTime = first.getTimeStamp(); // The time stamp of the first record is the reference point; all other time stamps are calculated relative to this.
		int count = temp.size();
		double[] times = new double[count];
		int j = 0;
		double[] rates = new double[count];
		double intConvert;
		for(Record record : temp) {
			intConvert = (double)(record.getTimeStamp() - startTime); // get the relative time stamp and cast as double
			intConvert = intConvert/1000;
			times[j] = intConvert;
			System.out.println(times[j]);
			
			
			rates[j] = (double)record.getHeartRate();
			j++;
		}
		displayLineChart(times,rates);
	}
			
		
		private void displayLineChart(double[] myTimes, double[] myRates)
		{
			// Creating an  XYSeries for Heart Rate
		     XYSeries hrSeries = new XYSeries("Heart Rate");
		     //XYSeries fakeSeries = new XYSeries("fake");
		     //double fake[] = new double[myTimes.length];
		     // Adding data to  hrSeries
		     for(int i=0;i<myTimes.length;i++){
		      hrSeries.add(myTimes[i], myRates[i]);
		      //fake[i] = 0;
		      //fakeSeries.add(myTimes[i], fake[i]);
		     }
		     
		     XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		     dataset.addSeries(hrSeries);
		     //dataset.addSeries(fakeSeries);
		      
		     XYSeriesRenderer hrRenderer = new XYSeriesRenderer();
		     hrRenderer.setColor(Color.GREEN);
		     hrRenderer.setPointStyle(PointStyle.DIAMOND);
		     hrRenderer.setFillPoints(true);
		     hrRenderer.setLineWidth(2);
		     hrRenderer.setDisplayChartValues(true);

		     
      /*
		     // Creating XYSeriesRenderer to customize incomeSeries
		     XYSeriesRenderer fakeRenderer = new XYSeriesRenderer();
		     fakeRenderer.setColor(Color.WHITE);
		     fakeRenderer.setPointStyle(PointStyle.CIRCLE);
		     fakeRenderer.setFillPoints(true);
		     fakeRenderer.setLineWidth(2);
		     fakeRenderer.setDisplayChartValues(true);
		*/
		     
		     XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		     multiRenderer.setXLabels(0);
		     multiRenderer.setChartTitle("Heart Rate vs Time");
		     multiRenderer.setXTitle("Timestamps (s)");
		     multiRenderer.setYTitle("Heart Rate");
		     multiRenderer.setZoomButtonsVisible(true);
		     
		     for(int i = 0; i < myTimes.length;i++)
		     {
		    	 String label;
		    	 label = String.valueOf(myTimes[i]);
		    	 multiRenderer.addXTextLabel(i+1,label);
		     }
		     
		     multiRenderer.addSeriesRenderer(hrRenderer);
		     //multiRenderer.addSeriesRenderer(fakeRenderer);
		     
		     Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
		     
		     startActivity(intent);
		};
		
	
	@Override
	/**
	 * The onCreate function is used to extract all the relevant data from the Records in the database and graph them.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph_data, menu);
		return true;
	}

}
