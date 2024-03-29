package me.cooper.rick.elementary.models.movestrategies;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import me.cooper.rick.elementary.listeners.ShakeAwareSensorListener;

public class SensorMoveStrategy extends ShakeAwareSensorListener implements MoveStrategy {

    private static final float X_SENSITIVITY = 2.0f;
    private static final float Y_SENSITIVITY = X_SENSITIVITY * 1.25f;

    private static final String DESCRIPTION = "Tilt";

    private View view;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener motionSensorListener = new TiltListener();

    public SensorMoveStrategy(View view, SensorManager sensorManager) {
        this.view = view;
        this.sensorManager = sensorManager;
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void move(float xSpeed, float ySpeed) {
        view.setX(view.getX() + xSpeed);
        view.setY(view.getY() - ySpeed); // Reverse y-axis
        view.invalidate();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void registerListener() {
        sensorManager.registerListener(motionSensorListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void unregisterListener() {
        sensorManager.unregisterListener(motionSensorListener);
    }

    private final class TiltListener extends ShakeAwareSensorListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);
            if (event.sensor.getType() == SENSOR_TYPE && !isShake()) {
                move(event.values[0] * X_SENSITIVITY, event.values[1] * Y_SENSITIVITY);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    }

}
