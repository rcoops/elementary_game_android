package me.cooper.rick.elementary.models;

public final class Player extends Score {

    private static final int SCORE_INCREMENT_INCREMENT = 10;

    private int lives = 10;
    private int scoreIncrement = SCORE_INCREMENT_INCREMENT;

    public Player(String playerName) {
        super(playerName);
    }

    public int getLives() {
        return lives;
    }

    public void adjustForRightAnswer() {
        score += scoreIncrement;
        scoreIncrement += SCORE_INCREMENT_INCREMENT;
    }

    public void adjustForWrongAnswer() {
        lives --;
        scoreIncrement = SCORE_INCREMENT_INCREMENT;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", lives=" + lives +
                ", scoreIncrement=" + scoreIncrement +
                '}';
    }

}
