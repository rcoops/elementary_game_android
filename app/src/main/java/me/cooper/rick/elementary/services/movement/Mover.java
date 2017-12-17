package me.cooper.rick.elementary.services.movement;

import android.hardware.SensorManager;
import android.view.View;

import me.cooper.rick.elementary.services.movement.strategies.MoveStrategy;
import me.cooper.rick.elementary.services.movement.strategies.SensorMoveStrategy;
import me.cooper.rick.elementary.services.movement.strategies.TouchMoveStrategy;
import me.cooper.rick.elementary.services.movement.util.CircularLinkedList;

public class Mover {

    private MoveStrategy activeMoveStrategy;

    private CircularLinkedList<MoveStrategy> moveStrategies = new CircularLinkedList<>();

    public Mover(SensorManager sensorManager, View view) {
        moveStrategies.add(new TouchMoveStrategy(view));
        if (sensorManager != null) {
            moveStrategies.add(new SensorMoveStrategy(view, sensorManager));
        }
        activateNextMoveStrategy();
    }

    public String getNextStrategyDescription() {
        return getStrategyDescription(moveStrategies.getNext());
    }

    public String getCurrentStategyDescription() {
        return getStrategyDescription(moveStrategies.getCurrent());
    }

    private String getStrategyDescription(MoveStrategy strategy) {
        return strategy == null ? null : strategy.getDescription();
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
