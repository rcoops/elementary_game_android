package me.cooper.rick.elementary.activities.game.movestrategies;

public interface MoveStrategy {

    void move(float x, float y);
    String getDescription();
    void unregisterListener();
    void registerListener();

}