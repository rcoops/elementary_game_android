package me.cooper.rick.elementary.activities.fragments.game;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import me.cooper.rick.elementary.activities.fragments.game.movestrategies.MoveStrategy;
import me.cooper.rick.elementary.activities.fragments.game.movestrategies.SensorMoveStrategy;
import me.cooper.rick.elementary.activities.fragments.game.movestrategies.TouchMoveStrategy;
import me.cooper.rick.elementary.activities.fragments.game.datastructures.CircularLinkedList;

public class MovementManager {

    private View view;
    private SensorManager sensorManager;
    private Sensor motionSensor;

    private MoveStrategy activeMoveStrategy;
    private CircularLinkedList<MoveStrategy> moveStrategies = new CircularLinkedList<>();

    public MovementManager(SensorManager sensorManager, View view) {
        this.sensorManager = sensorManager;
        this.view = view;

        if (sensorManager != null) {
            motionSensor = sensorManager.getDefaultSensor(MotionSensorListener.SENSOR_TYPE);
            moveStrategies.add(new SensorMoveStrategy(view, sensorManager));
        }
        TouchMoveStrategy touchMoveStrategy = new TouchMoveStrategy(view);
        moveStrategies.add(touchMoveStrategy);
        activeMoveStrategy = moveStrategies.cycleNext();
        activateNextMoveStrategy();
    }

    private void move(float x, float y) {
        activeMoveStrategy.move(x, y);
    }

    public String getMoveMenuItemText() {
        MoveStrategy nextStrategy = moveStrategies.getNext();
        return nextStrategy == null ? null : nextStrategy.getDescription();
    }

    public void stopMoving() {
        activeMoveStrategy.unregisterListener();
    }

    public void startMoving() {
        activeMoveStrategy.registerListener();
    }

    public void activateNextMoveStrategy() {
        stopMoving();
        activeMoveStrategy = moveStrategies.cycleNext();
        startMoving();
    }

    private class MotionSensorListener implements SensorEventListener {

        static final int SENSOR_TYPE = Sensor.TYPE_ACCELEROMETER;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == SENSOR_TYPE) {
                move(event.values[0], event.values[1]);
            }
            view.invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    }

    private class OnTouchListener implements View.OnTouchListener {
        private float dx = 0, dy = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == view) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dx = event.getX() - view.getX();
                        dy = event.getY() - view.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(event.getX() - dx, event.getY() - dy);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
            return true;
        }
    }

}
