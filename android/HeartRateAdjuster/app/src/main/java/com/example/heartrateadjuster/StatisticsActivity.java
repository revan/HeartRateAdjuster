package com.example.heartrateadjuster;
/**
 * This StatisticsActivity is the entry point for the data management module, and it provides three basic functions with which other modules can interface.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
        // Adding data to  hrSeries
        for(int i=0;i<myTimes.length;i++){
            hrSeries.add(myTimes[i], myRates[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(hrSeries);

        XYSeriesRenderer hrRenderer = new XYSeriesRenderer();

        hrRenderer.setColor(Color.GREEN);
        hrRenderer.setPointStyle(PointStyle.DIAMOND);
        hrRenderer.setFillPoints(true);
        hrRenderer.setLineWidth(2);
        hrRenderer.setDisplayChartValues(true);

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Heart Rate vs Time");
        multiRenderer.setXTitle("Timestamps (s)");
        multiRenderer.setYTitle("Heart Rate");
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.BLACK);
        multiRenderer.setZoomButtonsVisible(true);

        for(int i = 0; i < myTimes.length;i++)
        {
            String label;
            label = String.valueOf(myTimes[i]);
            multiRenderer.addXTextLabel(i+1,label);
        }

        multiRenderer.addSeriesRenderer(hrRenderer);

        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);

        startActivity(intent);
    };

    /**
     * Generates Pie Chart showing artists
     * @param view
     */
	public void graphArtist(View view) {
        DataAssembler db = new DataAssembler(this);

        HashSet artists = new HashSet();
        List<Record> temp = db.getAllRecords();
        ArrayList<String> myArtists = new ArrayList<String>(); // has all artists

        for(Record record : temp)
        {
            artists.add(record.getArtist());
            myArtists.add(record.getArtist());
        }
        // Convert to an array. Has unique artists
        String[] uniqueArtists = (String[]) artists.toArray(new String[artists.size()]);

        double[] frequencies = new double[uniqueArtists.length];

        for(int i = 0; i < uniqueArtists.length; i++)
        {
            int count = Collections.frequency(myArtists, uniqueArtists[i]);
            double percentage = (count / ((double)temp.size())) * 100;
            frequencies[i] = percentage;

        }

        int[] colors = {Color.BLUE,Color.GREEN,Color.RED,Color.CYAN,Color.MAGENTA};
        CategorySeries distributionSeries = new CategorySeries("Frequencies of Artists");
        for(int i = 0; i < frequencies.length;i++)
        {
            distributionSeries.add(uniqueArtists[i],frequencies[i]);
        }


        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for(int i = 0; i < frequencies.length; i++)
        {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i%5]);
            seriesRenderer.setDisplayChartValues(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setChartTitle("Frequencies of Artists");
        defaultRenderer.setChartTitleTextSize(50);
        defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
	defaultRenderer.setDisplayValues(true);

        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "PieChart");

        startActivity(intent);

    }

    /**
     * Generate Pie Charts for Song
     * @param view
     */
	public void graphSong(View view) {
        DataAssembler db = new DataAssembler(this);

        HashSet songs = new HashSet();
        List<Record> temp = db.getAllRecords();
        ArrayList<String> mySongs = new ArrayList<String>(); // has all songs

        for(Record record : temp)
        {
            songs.add(record.getSong());
            mySongs.add(record.getSong());
        }
        // Convert to an array. Has unique songs
        String[] uniqueSongs = (String[]) songs.toArray(new String[songs.size()]);

        double[] frequencies = new double[uniqueSongs.length];

        for(int i = 0; i < uniqueSongs.length; i++)
        {
            int count = Collections.frequency(mySongs,uniqueSongs[i]);
            double percentage = (count / ((double)temp.size())) * 100;
            frequencies[i] = percentage;

        }

        int[] colors = {Color.BLUE,Color.GREEN,Color.RED,Color.CYAN,Color.MAGENTA};
        CategorySeries distributionSeries = new CategorySeries("Frequencies of Songs");
        for(int i = 0; i < frequencies.length;i++)
        {
            distributionSeries.add(uniqueSongs[i],frequencies[i]);
        }


        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for(int i = 0; i < frequencies.length; i++)
        {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i%5]);
            seriesRenderer.setDisplayChartValues(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setChartTitle("Frequencies of Songs");
        defaultRenderer.setChartTitleTextSize(50);
        defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
	defaultRenderer.setDisplayValues(true);

        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "PieChart");

        startActivity(intent);

    }

    /**
     * Generate Pie Charts for Genres
     * @param view
     */
	public void graphGenre(View view) {
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
            int count = Collections.frequency(myGenres,uniqueGenres[i]);
            double percentage = (count / ((double)temp.size())) * 100;
            frequencies[i] = percentage;

        }

        int[] colors = {Color.BLUE,Color.GREEN,Color.RED,Color.CYAN,Color.MAGENTA};
        CategorySeries distributionSeries = new CategorySeries("Frequencies of Genres");
        for(int i = 0; i < frequencies.length;i++)
        {
            distributionSeries.add(uniqueGenres[i],frequencies[i]);
        }


        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for(int i = 0; i < frequencies.length; i++)
        {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i%5]);
            seriesRenderer.setDisplayChartValues(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setChartTitle("Frequencies of Genres");
        defaultRenderer.setChartTitleTextSize(50);
        defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
	defaultRenderer.setDisplayValues(true);

        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "PieChart");

        startActivity(intent);

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
