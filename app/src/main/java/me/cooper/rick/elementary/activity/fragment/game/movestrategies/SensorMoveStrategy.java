package me.cooper.rick.elementary.activity.fragment.game.movestrategies;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import me.cooper.rick.elementary.listeners.AcceleroListener;

public class SensorMoveStrategy extends AcceleroListener implements MoveStrategy {

    private static final float X_SENSITIVITY = 4;
    private static final float Y_SENSITIVITY = 5;

    private static final String DESCRIPTION = "Motion Sensor";

    private View view;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener motionSensorListener = new MotionSensorListener();

    public SensorMoveStrategy(View view, SensorManager sensorManager) {
        this.view = view;
        this.sensorManager = sensorManager;
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void move(float xSensorValue, float ySensorValue) {
        float xSpeed = xSensorValue * X_SENSITIVITY;
        float ySpeed = ySensorValue * Y_SENSITIVITY;
        view.setX(view.getX() + xSpeed);
        view.setY(view.getY() - ySpeed); // Reverse y-axis
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

    private final class MotionSensorListener extends AcceleroListener implements SensorEventListener {

        static final int SENSOR_TYPE = Sensor.TYPE_ACCELEROMETER;

        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);
            if (event.sensor.getType() == SENSOR_TYPE && !isShake()) {
                move(event.values[0], event.values[1]);
            }
            view.invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    }

}
