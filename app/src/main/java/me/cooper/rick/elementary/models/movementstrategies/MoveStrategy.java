package me.cooper.rick.elementary.models.movementstrategies;

public interface MoveStrategy {

    void move(float x, float y);
    String getDescription();
    void unregisterListener();
    void registerListener();

}