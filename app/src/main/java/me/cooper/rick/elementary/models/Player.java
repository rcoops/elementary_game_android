package me.cooper.rick.elementary.models;

public final class Player extends Score {

    private int lives = 10;

    public Player(String playerName) {
        super(playerName);
    }

    public int getLives() {
        return lives;
    }

    public void incrementLives() {
        lives++;
    }

    public void decrementLives() {
        lives--;
    }

}
