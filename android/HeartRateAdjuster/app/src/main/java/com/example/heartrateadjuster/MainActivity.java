package com.example.heartrateadjuster;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main screen of the application.
 * Initializes chestStrap and audioSystem objects and sets up the UI with callbacks.
 * @author Revan
 */

public class MainActivity extends Activity implements CalculateTargetFragment.CalculateListener{

    /**
     * Chest strap object, instantiated as a {@link com.example.heartrateadjuster.ChestStrapFake}
     */
    public IHeartRateDevice chestStrap = new ChestStrapFake();

    /**
     * Audio system object, instantiated as a {@link com.example.heartrateadjuster.AudioSystemFake}
     */
    public IAudioSystem audioSystem = new AudioSystemFake();

    /**
     * Timer used to update heart rate display once a second.
     */
    private Timer timer = new Timer();

    //constants
    private static int PERIOD = 1000;
    private static String up = "Raising";
    private static String down = "Lowering";

    /**
     * Non-final pointer to peak selector, needed to use popup
     */
    NumberPicker np22;

    /**
     * Database Object
     */
    private DataAssembler db = new DataAssembler(this);

    /**
     * Android fundamental method, used to instantiate all UI elements with callbacks.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize number pickers. np1 is low, np2 is high
        final NumberPicker np1 = (NumberPicker)findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker)findViewById(R.id.numberPicker2);
        np22 = np2;
        final Button toggleDirection = (Button)findViewById(R.id.button1);
        np1.setMinValue(0);
        np1.setMaxValue(200);
        np1.setValue(70);
        np1.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if(scrollState==NumberPicker.OnScrollListener.SCROLL_STATE_IDLE){
                    if (toggleDirection.getText().equals(down)) {
                        updateTargetHeartrate(np1.getValue());
                    }
                }
            }
        });

        np2.setMinValue(50);
        np2.setMaxValue(200);
        np2.setValue(120);
        np2.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if(scrollState==NumberPicker.OnScrollListener.SCROLL_STATE_IDLE){
                    if (toggleDirection.getText().equals(up)) {
                        updateTargetHeartrate(np2.getValue());
                    }
                }
            }
        });

        //initialize toggleDirection button. Toggles text and calls updateTargetHeartrate()
        toggleDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleDirection.getText().equals(up)){
                    toggleDirection.setText(down);
                    updateTargetHeartrate(np1.getValue());
                }else{
                    toggleDirection.setText(up);
                    updateTargetHeartrate(np2.getValue());
                }
            }
        });

        //initialize SeekBar for faking Chest Strap
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1);
        seekBar.setMax(200);
        seekBar.setProgress(60);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((ChestStrapFake)chestStrap).setCurrentHeartRate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //initialize Timer to update heart rate display once a second
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Update heart rate
                        TextView textView = (TextView)findViewById(R.id.textView4);
                        textView.setText(chestStrap.getCurrentHeartRate() + " BPM");

                        //store nowPlaying in database
                        Record nowPlaying = audioSystem.getNowPlaying();
                        nowPlaying.setHeartRate(chestStrap.getCurrentHeartRate());
                        db.addRecord(nowPlaying);

                        //If heart rate critical, pause
                        if(chestStrap.getCurrentHeartRate() > np2.getValue()+30){
                            criticalStop();
                        }
                    }
                });
            }
        }, 200, PERIOD);

        //initialize play button. Calls togglePlayback()
        final ImageButton togglePlay = (ImageButton)findViewById(R.id.imageButton1);
        togglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayback();
            }
        });

        //initialize skip button. Calls skipTrack()
        final ImageButton skipTrack = (ImageButton)findViewById(R.id.imageButton2);
        skipTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTrack();
            }
        });

        //initialize set button
        final Button buttonSet = (Button)findViewById(R.id.button2);
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                np1.setValue(chestStrap.getCurrentHeartRate());
            }
        });

        //initialize calculate button
        final Button buttonCalculate = (Button)findViewById(R.id.button3);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalculatePopup();
            }
        });

        //initialize fake now playing button. Calls updateNowPlaying with current time.
        final ImageButton fakeNowPlaying = (ImageButton)findViewById(R.id.imageButton);
        fakeNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNowPlaying();
            }
        });

        updateNowPlaying();

        List<Record> recordList = db.getAllRecords(); // retrieves all the records that are currently in the database
        for(Record record : recordList) { // deletes all the records from the database
            db.deleteRecord(record);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_statistics) {
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Passes on new heart rate value to audioSystem.
     * @param val Heart Rate value from UI.
     */
    void updateTargetHeartrate(int val){
        Toast.makeText(this, "updateTargetHeartrate("+val+")", Toast.LENGTH_SHORT).show();
        audioSystem.setTargetHeartRate(val);
    }

    /**
     * Passes on togglePlayback command to audioSystem.
     */
    void togglePlayback(){
        Toast.makeText(this, "togglePlayback()", Toast.LENGTH_SHORT).show();
        audioSystem.togglePlayback();
    }

    /**
     * Passes on criticalStop command to audioSystem.
     */
    void criticalStop(){
        Toast.makeText(this, "criticalStop()", Toast.LENGTH_SHORT).show();
        audioSystem.criticalStop();
    }

    /**
     * Passes on skipTrack command to audioSystem.
     */
    void skipTrack(){
        Toast.makeText(this, "skipTrack()", Toast.LENGTH_SHORT).show();
        audioSystem.skipTrack();
    }

    /**
     * Updates UI with now playing information from audioSystem.
     */
    void updateNowPlaying(){
        TextView textView = (TextView)findViewById(R.id.textView3);
        textView.setText("Now Playing: "+audioSystem.getNowPlaying().getSong());
    }

    /**
     * Pops up {@link com.example.heartrateadjuster.CalculateTargetFragment} UI
     * to calculate target heartrate
     */
    void showCalculatePopup(){
        DialogFragment dialog = new CalculateTargetFragment();
        dialog.show(getFragmentManager(), "calculate");
    }

    /**
     * Callback for {@link com.example.heartrateadjuster.CalculateTargetFragment} pop-up
     * @param dialog the popup object
     * @param newTarget the new peak heartrate
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int newTarget) {
        np22.setValue(newTarget);
    }
}
