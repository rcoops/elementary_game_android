package me.cooper.rick.elementary.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    public static final Creator<Score> CREATOR = new ParcelableScoreCreator();

    protected final String playerName;
    protected int score = 0;

    public Score(String playerName) {
        this.playerName = playerName;
    }

    protected Score(Parcel parcel) {
        playerName = parcel.readString();
        score = parcel.readInt();
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean hasScore() {
        return score > 0;
    }

    public int getScore() {
        return score;
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
