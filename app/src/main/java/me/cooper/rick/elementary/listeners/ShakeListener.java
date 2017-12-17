package me.cooper.rick.elementary.listeners;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public final class ShakeListener extends AcceleroListener implements SensorEventListener {

    private OnShakeListener onShakeListener;

    public ShakeListener(OnShakeListener onShakeListener) {
        this.onShakeListener = onShakeListener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        super.onSensorChanged(event);

        if (event.sensor.getType() == SENSOR_TYPE && isShake()) {
            onShakeListener.onShake();
        }
    }

    public interface OnShakeListener {
        void onShake();
    }

}
