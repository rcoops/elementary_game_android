package me.cooper.rick.elementary.constants;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class Constants {

    public static final DatabaseReference SCORES_DB = FirebaseDatabase
            .getInstance().getReference("scores");

    public static final String PLAYER_INTENT_TAG = "player";

    public static final int SCORE_BASE_INCREMENT = 100;
    public static final int SCORE_INCREMENT_INCREMENT = 10;

    public static final long[] VIBRATE_CORRECT = new long[] { 0, 100, 50, 800 };
    public static final long[] VIBRATE_WRONG = new long[] { 0, 300, 50, 300, 50, 600 };
    public static final long[] VIBRATE_QUIT = new long[] { 0, 600, 50, 100, 50, 1200 };

    private Constants() {}

}
