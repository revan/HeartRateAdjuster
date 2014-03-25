package com.example.heartrateadjuster;
/*For use with a UI element for faking Audio System
 *Implements interface, so dependent systems don't have to know
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

    //Return current time as title
    public String getNowPlaying(){
        return ""+System.currentTimeMillis();
    }
}
