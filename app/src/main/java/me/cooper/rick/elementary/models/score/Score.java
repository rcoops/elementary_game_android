package me.cooper.rick.elementary.models.score;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

public class Score implements Parcelable, Comparable<Score> {

    public static final int SCORE_BASE_INCREMENT = 100;
    public static final int SCORE_INCREMENT_INCREMENT = 50;

    public static final Creator<Score> CREATOR = new ParcelableScoreCreator();

    protected String playerName;
    protected int score = 0;

    public Score() {
        // Mandatory empty constructor for firebase parse
    }

    public Score(String playerName) {
        this.playerName = playerName;
    }

    public Score(String playerName, int score) {
        this(playerName);
        this.score = score;
    }

    protected Score(Parcel parcel) {
        playerName = parcel.readString();
        score = parcel.readInt();
    }

    public String getPlayerName() {
        return playerName;
    }

    @SuppressWarnings("unused") // Used for Firebase parse
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @SuppressWarnings("unused") // Used for Firebase parse
    public void setScore(int score) {
        this.score = score;
    }

    public boolean hasScore() {
        return score > 0;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Score score1 = (Score) o;

        return  score == score1.score
                && playerName.equals(score1.playerName);
    }

    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return "Score{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(@NonNull Score other) {
        int comparision = other.getScore() - score;
        if (comparision == 0) {
            comparision = playerName.compareTo(other.getPlayerName());
        }
        return comparision;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerName);
        dest.writeInt(score);
    }

    private static class ParcelableScoreCreator implements Creator<Score> {
        @Override
        public Score createFromParcel(Parcel parcel) {
            return new Score(parcel);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }

    }
}
