package com.example.heartrateadjuster;
/**
 * Interface with the Chest Strap.
 */

public interface IHeartRateDevice {
    /**
     * Gets current heart rate from device.
     * @return Current heart rate.
     */
    public int getCurrentHeartRate();
}
