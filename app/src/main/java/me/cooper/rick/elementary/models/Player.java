package me.cooper.rick.elementary.models;

public final class Player {

    private int lives = 10;
    private Score score;

    public Player(String playerName) {
        this.score = new Score(playerName);
    }

    public String getPlayerName() {
        return score.getName();
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
