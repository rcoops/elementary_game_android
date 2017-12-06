package me.cooper.rick.elementary.constants;

import java.util.Random;

public final class Constants {

    public static final String PLAYER_INTENT_TAG = "player";

    public static final int NO_OF_ELEMENTS = 4;
    public static final int SCORE_BASE_INCREMENT = 100;
    public static final int SCORE_INCREMENT_INCREMENT = 10;

    public static final String DB_NAME = "scores";
    public static final String SCORE_FIELD_NAME = "score";
    public static final String GAME_MUSIC = "music";
    public static final String SOUND_WRONG = "wrong";
    public static final String SOUND_GAME_OVER = "game_over";
    public static final String SOUND_RIGHT = "right";

    public static final int HIGH_SCORE_ENTRIES_LIMIT = 20;

    public static final float MUSIC_VOLUME = 0.6f;

    public static final Random RAND = new Random();

    private Constants() {}

}
