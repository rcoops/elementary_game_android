package me.cooper.rick.elementary.models;

import android.os.Parcel;

import com.google.firebase.database.Exclude;

public final class Player extends Score {

    public static final String PLAYER_INTENT_TAG = "player";

    private static final int NEW_LIFE_SCORE_THRESHOLD = SCORE_INCREMENT_INCREMENT * 5;

    public static final Creator<Player> CREATOR = new ParcelablePlayerCreator();

    private int scoreIncrement = SCORE_BASE_INCREMENT;
    private int lives = 10;

    public Player(String playerName) {
        super(playerName);
    }

    // Don't need lives or score outside game activity
    public Player(Parcel parcel) {
        super(parcel);
    }

    @Exclude // Prevent Firebase from recording this
    public int getLives() {
        return lives;
    }

    public void adjustForRightAnswer() {
        score += scoreIncrement;
        scoreIncrement += SCORE_INCREMENT_INCREMENT;
        if (shouldAddLife()) {
            lives++;
        }
    }

    public void adjustForWrongAnswer() {
        lives --;
        scoreIncrement = SCORE_BASE_INCREMENT;
    }

    private boolean shouldAddLife() {
        int currentIncrementOverBase = scoreIncrement - SCORE_BASE_INCREMENT;
        return currentIncrementOverBase % NEW_LIFE_SCORE_THRESHOLD == 0;
    }

    // Don't need lives or score outside game activity
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
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

    private static class ParcelablePlayerCreator implements Creator<Player> {

        @Override
        public Player createFromParcel(Parcel parcel) {
            return new Player(parcel);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }

    }

}
