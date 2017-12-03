package me.cooper.rick.elementary.models;

import android.os.Parcel;

import com.google.firebase.database.Exclude;

import static me.cooper.rick.elementary.constants.Constants.SCORE_BASE_INCREMENT;
import static me.cooper.rick.elementary.constants.Constants.SCORE_INCREMENT_INCREMENT;

public final class Player extends Score {


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

    @Exclude
    public int getLives() {
        return lives;
    }

    public void adjustForRightAnswer() {
        score += scoreIncrement;
        scoreIncrement += SCORE_INCREMENT_INCREMENT;
    }

    public void adjustForWrongAnswer() {
        lives --;
        scoreIncrement = SCORE_BASE_INCREMENT;
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
