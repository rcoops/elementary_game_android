package me.cooper.rick.elementary.services.movement.strategies;

public interface MoveStrategy {

    void move(float x, float y);
    String getDescription();
    void unregisterListener();
    void registerListener();

}