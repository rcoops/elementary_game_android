package me.cooper.rick.elementary.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public abstract class AcceleroListener implements SensorEventListener {

    protected static final int SENSOR_TYPE = Sensor.TYPE_ACCELEROMETER;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
    private long mShakeTimestamp;
    private int mShakeCount;

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
        final long now = System.currentTimeMillis();
        boolean exceedsGForce = gForce > SHAKE_THRESHOLD_GRAVITY;
        boolean isWIthinShakeTime = mShakeTimestamp + SHAKE_SLOP_TIME_MS > now;

        // reset the shake count after 3 seconds of no shakes
        if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
            mShakeCount = 0;
        }

        mShakeTimestamp = now;
        mShakeCount++;
        return isWIthinShakeTime && exceedsGForce;
    }

}
