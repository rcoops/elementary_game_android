package me.cooper.rick.elementary.models;

public class Score {

    protected final String playerName;
    protected int score = 0;

    public Score(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int adjustment) {
        score += adjustment;
    }

}
