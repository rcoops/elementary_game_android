package me.cooper.rick.elementary.constants;

import java.util.Random;

public final class Constants {

    public static final int NO_OF_ELEMENTS = 4;

    public static final int SCORE_BASE_INCREMENT = 100;

    public static final int SCORE_INCREMENT_INCREMENT = 50;
    public static final int HIGH_SCORE_ENTRIES_LIMIT = 20;

    public static final String DB_NAME = "scores";

    public static final String PLAYER_INTENT_TAG = "player";

    public static final String SCORE_FIELD_NAME = "score";

    public static final String SOUND_WRONG = "wrong";
    public static final String SOUND_GAME_OVER = "game_over";
    public static final String SOUND_RIGHT = "correct";
    public static final String SOUND_CLICK = "click";
    public static final String SOUND_DRAWER = "drawer";

    public static final String FRAG_TAG_SETTINGS = "settings";
    public static final String FRAG_TAG_SCORES = "highScores";
    public static final String FRAG_TAG_INSTRUCTIONS = "instructions";
    public static final String FRAG_TAG_NEW_GAME = "newGame";
    public static final String FRAG_TAG_QUIT_GAME = "quitGame";

    public static final String PREF_VOL_MUSIC = "pref_volume_music";
    public static final String PREF_VOL_EFFECTS = "pref_volume_effects";
    public static final String PREF_TOG_VIBRATE = "pref_toggle_vibrate";

    public static final int DEFAULT_VOLUME = 5;

    public static final Random RAND = new Random();

    private Constants() {}

}
