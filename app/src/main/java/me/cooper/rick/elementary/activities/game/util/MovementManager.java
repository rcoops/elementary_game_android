package me.cooper.rick.elementary.activities.game.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import me.cooper.rick.elementary.activities.game.movestrategies.MoveStrategy;
import me.cooper.rick.elementary.activities.game.movestrategies.SensorMoveStrategy;
import me.cooper.rick.elementary.activities.game.movestrategies.TouchMoveStrategy;
import me.cooper.rick.elementary.activities.game.movestrategies.datastructures.CircularLinkedList;

public class MovementManager {

    private MoveStrategy activeMoveStrategy;
    private CircularLinkedList<MoveStrategy> moveStrategies = new CircularLinkedList<>();

    public MovementManager(SensorManager sensorManager, View view) {
        if (sensorManager != null) {
            moveStrategies.add(new SensorMoveStrategy(view, sensorManager));
        }
        TouchMoveStrategy touchMoveStrategy = new TouchMoveStrategy(view);
        moveStrategies.add(touchMoveStrategy);
        activeMoveStrategy = moveStrategies.cycleNext();
        activateNextMoveStrategy();
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

}
