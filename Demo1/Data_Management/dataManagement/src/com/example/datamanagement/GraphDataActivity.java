package com.example.datamanagement;
/**
 * This Android activity essentially is a script that runs the graphing of the data.
 */
import java.io.File;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/**
 * The GraphDataActivity is called when the Graph button is pressed.
 * @author nikhil
 *
 */
public class GraphDataActivity extends Activity {

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
		double intConvert;
		for(Record record : temp) {
			intConvert = (double)(record.getTimeStamp() - startTime); // get the relative time stamp and cast as double
			intConvert = intConvert/1000;
			times[j] = intConvert;
			System.out.println(times[j]);
			j++;
		}
			
		
		
		try {
			DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset(); // create the line_chart object
			int x = 0;
			for(Record record : temp)
			{				
            /* Step - 1: Define the data for the line chart  */
				line_chart_dataset.addValue(record.getHeartRate(), "schools", String.valueOf(times[x])); // add ordered pairs to the data set.
				x++;
			}
                            
            
            /* Step -2:Define the JFreeChart object to create line chart */
            JFreeChart lineChartObject=ChartFactory.createLineChart("Heart Rate vs Time","Time (milliseconds)","Heart Rate",line_chart_dataset,PlotOrientation.VERTICAL,true,true,false);                
                      
            
            /* Step -3 : Write line chart to a file */               
             int width=640; /* Width of the image */
             int height=480; /* Height of the image */                
             File lineChart=new File("line_Chart_example.png");              
             ChartUtilities.saveChartAsPNG(lineChart,lineChartObject,width,height); // save the graph as a .png file
             System.out.println("printed the picture");
		}
		catch (Exception i)
		{
			 System.out.println(i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph_data, menu);
		return true;
	}

}
