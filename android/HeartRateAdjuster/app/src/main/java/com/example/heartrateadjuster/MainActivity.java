package com.example.heartrateadjuster;

import android.app.Activity;
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

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    public IHeartRateDevice chestStrap = new ChestStrapFake(); //Chest Strap
    private Timer timer = new Timer();
    private static int PERIOD = 1000;
    private static String up = "Raising";
    private static String down = "Lowering";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize number pickers. np1 is low, np2 is high
        final NumberPicker np1 = (NumberPicker)findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker)findViewById(R.id.numberPicker2);
        final Button toggleDirection = (Button)findViewById(R.id.button1);
        np1.setMinValue(40);
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
                        TextView textView = (TextView)findViewById(R.id.textView4);
                        textView.setText(chestStrap.getCurrentHeartRate() + " BPM");
                    }
                });
            }
        }, 200, PERIOD);

        //initialize play button. Calls togglePlayback()
        ImageButton togglePlay = (ImageButton)findViewById(R.id.imageButton1);
        togglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayback();
            }
        });

        //initialize skip button. Calls skipTrack()
        ImageButton skipTrack = (ImageButton)findViewById(R.id.imageButton2);
        skipTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTrack();
            }
        });


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
            //TODO start statistics activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //methods interfacing with other classes
    void updateTargetHeartrate(int val){
        Toast.makeText(this, "updateTargetHeartrate("+val+")", Toast.LENGTH_SHORT).show();
    }

    void togglePlayback(){
        Toast.makeText(this, "togglePlayback()", Toast.LENGTH_SHORT).show();
    }

    void skipTrack(){
        Toast.makeText(this, "skipTrack()", Toast.LENGTH_SHORT).show();
    }

    void updateNowPlaying(String nowPlaying){
        //TODO
    }
}
