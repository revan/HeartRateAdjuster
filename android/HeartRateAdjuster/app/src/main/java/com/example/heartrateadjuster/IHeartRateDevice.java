package com.example.heartrateadjuster;
/**
 * Interface with the Chest Strap.
 * @author Revan
 */

public interface IHeartRateDevice {
    /**
     * Gets current heart rate from device.
     * @return Current heart rate.
     */
    public int getCurrentHeartRate();
}
