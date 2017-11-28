package me.cooper.rick.elementary.models;

import android.os.Parcel;
import android.os.Parcelable;

public final class Player implements Parcelable {

    public static final Parcelable.Creator<Player> CREATOR = new ParcelableStringCreator();

    private int id;
    private final String playerName;
    private int score = 0;
    private int lives = 10;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    private Player(Parcel parcel) {
        this.playerName = parcel.readString();
        this.score = parcel.readInt();
        this.lives = parcel.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLives() {
        return lives;
    }

    public void incrementLives() {
        lives++;
    }

    public void decrementLives() {
        lives--;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", lives=" + lives +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(playerName);
        parcel.writeInt(score);
        parcel.writeInt(lives);
    }

    private static class ParcelableStringCreator implements Parcelable.Creator<Player> {

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
