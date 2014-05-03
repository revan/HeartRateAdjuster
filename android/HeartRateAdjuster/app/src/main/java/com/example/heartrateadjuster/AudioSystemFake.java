package com.example.heartrateadjuster;

import java.util.Random;

/**
 * Implementation of {@link com.example.heartrateadjuster.IAudioSystem} for faking Audio System with UI element.
 */
public class AudioSystemFake implements IAudioSystem{
    Random rn = new Random();
    public void setTargetHeartRate(int target){
        return;
    }
    public void skipTrack(){
        return;
    }
    public void togglePlayback(){
        return;
    }

    /**
     * Returns system time for a unique String.
     * @return Current system time as String.
     */
    public Record getNowPlaying(){
        return new Record(0, (int)(System.currentTimeMillis()*1000),"Artist #"+rn.nextInt(10),
                "Genre #"+rn.nextInt(3), "Song #"+rn.nextInt(100));
    }
}
