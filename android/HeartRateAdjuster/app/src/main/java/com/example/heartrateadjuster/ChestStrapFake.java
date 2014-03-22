package com.example.heartrateadjuster;
/*For use with a UI element for faking chest strap data
 *Implements interface, so dependent systems don't have to know
 */
public class ChestStrapFake implements IHeartRateDevice{
    private int heartrate=60;
    public int getCurrentHeartRate(){
        return heartrate;
    }
    public void setCurrentHeartRate(int heartrate) {this.heartrate = heartrate;}
}
