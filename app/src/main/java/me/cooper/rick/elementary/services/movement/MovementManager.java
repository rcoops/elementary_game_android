package me.cooper.rick.elementary.services.movement;

import android.hardware.SensorManager;
import android.view.View;

import me.cooper.rick.elementary.services.movement.strategies.MoveStrategy;
import me.cooper.rick.elementary.services.movement.strategies.SensorMoveStrategy;
import me.cooper.rick.elementary.services.movement.strategies.TouchMoveStrategy;
import me.cooper.rick.elementary.services.movement.util.CircularLinkedList;

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
