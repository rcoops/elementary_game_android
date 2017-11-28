package me.cooper.rick.elementary.activity.fragment.game.movestrategies;

public interface MoveStrategy {

    void move(float x, float y);
    String getDescription();
    void unregisterListener();
    void registerListener();

}