package com.example.heartrateadjuster;
/**
 * Implementation of {@link com.example.heartrateadjuster.IAudioSystem} for faking Audio System with UI element.
 */
public class AudioSystemFake implements IAudioSystem{
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
    public String getNowPlaying(){
        return ""+System.currentTimeMillis();
    }
}
