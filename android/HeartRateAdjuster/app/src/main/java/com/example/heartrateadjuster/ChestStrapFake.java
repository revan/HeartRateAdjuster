package com.example.heartrateadjuster;
/**
 * Implementation of {@link com.example.heartrateadjuster.IHeartRateDevice} for faking chest strap data with UI element.
 */
public class ChestStrapFake implements IHeartRateDevice{
    /**
     * Internally stored heart rate measurement.
     */
    private int heartrate=60;

    /**
     * Gets the internally stored heart rate measurement.
     * @return The internally stored heart rate measurement.
     */
    public int getCurrentHeartRate(){
        return heartrate;
    }

    /**
     * Sets the internally stored heart rate measurement.
     * @param heartrate The new heart rate.
     */
    public void setCurrentHeartRate(int heartrate){
        this.heartrate = heartrate;
    }
}
