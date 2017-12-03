package me.cooper.rick.elementary.constants;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class Constants {

    public static final DatabaseReference SCORES_DB = FirebaseDatabase
            .getInstance().getReference("scores");

    public static final String PLAYER_INTENT_TAG = "player";

    public static final int SCORE_BASE_INCREMENT = 100;
    public static final int SCORE_INCREMENT_INCREMENT = 10;

    private Constants() {}

}
