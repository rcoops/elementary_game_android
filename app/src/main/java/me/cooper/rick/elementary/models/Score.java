package me.cooper.rick.elementary.models;

public final class Score {

    private final String name;
    private int score = 0;

    public Score(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int adjustment) {
        score += adjustment;
    }

}
