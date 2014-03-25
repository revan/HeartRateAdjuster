package com.example.heartrateadjuster;
//Interface to the Audio System
public interface IAudioSystem {
    public void setTargetHeartRate(int target);
    public void skipTrack();
    public void togglePlayback();
    public String getNowPlaying();
}
