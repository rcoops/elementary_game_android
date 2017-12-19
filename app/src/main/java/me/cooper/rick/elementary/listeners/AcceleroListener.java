package me.cooper.rick.elementary.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Provides basic inheritable functionality to distinguish between a shake and a tilt.
 */
public abstract class AcceleroListener implements SensorEventListener {

    protected static final int SENSOR_TYPE = Sensor.TYPE_ACCELEROMETER;

    private static final float SHAKE_THRESHOLD = 2.2F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private long lastShake;
    private int shakeCount;

    private double gForce = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
        float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
        float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

        gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    protected boolean isShake() {
//        if (shakeCount > 3) {
//            shakeCount = 0;
//            return false;
//        }
        final long now = System.currentTimeMillis();
        boolean exceedsGForce = gForce > SHAKE_THRESHOLD;
        boolean isWithinShakeTime = lastShake + SHAKE_SLOP_TIME_MS > now;

        // reset the shake count after 3 seconds of no shakes
        if (lastShake + SHAKE_COUNT_RESET_TIME_MS < now) {
            shakeCount = 0;
        }

        lastShake = now;
        shakeCount++;
        return isWithinShakeTime && exceedsGForce;
    }

}
