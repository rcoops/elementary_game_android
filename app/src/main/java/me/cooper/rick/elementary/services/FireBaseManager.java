package me.cooper.rick.elementary.services;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.cooper.rick.elementary.models.score.Player;
import me.cooper.rick.elementary.models.score.Score;

public class FireBaseManager {

    private static FireBaseManager instance;

    private static final int HIGH_SCORE_ENTRIES_LIMIT = 20;

    private static final String SCORE_FIELD_NAME = "score";
    private static final String DB_NAME = "scores";

    private DatabaseReference scoresDb;

    private List<Score> highScores;
    private FireBaseListener scoreFireBaseListener;

    public static FireBaseManager getInstance() {
        if(instance == null) {
            instance = new FireBaseManager();
        }
        return instance;
    }

    private FireBaseManager() {
        highScores = new ArrayList<>();
        scoresDb = FirebaseDatabase.getInstance().getReference(DB_NAME);

        scoresDb.orderByChild(SCORE_FIELD_NAME)
                .limitToLast(HIGH_SCORE_ENTRIES_LIMIT)
                .addChildEventListener(new HighScoreChildEventListener());
    }

    public void addHighScoreListListener(FireBaseListener listener) {
        this.scoreFireBaseListener = listener;
    }

    public void saveScore(Player player) {
        if (player.hasScore()) {
            String newScore = scoresDb.push().getKey();
            scoresDb.child(newScore).setValue(player);
        }
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    private void notifyListener() {
        if (scoreFireBaseListener != null) {
            scoreFireBaseListener.onFireBaseChange();
        }
    }

    private class HighScoreChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Score score = dataSnapshot.getValue(Score.class);
            highScores.add(score);
            Collections.sort(highScores);
            notifyListener();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            // Children wont change
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            highScores.remove(highScores.size() - 1); // last item is always lowest score
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            // Dealt with by limit and delete
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // No implementation necessary
        }

    }

    public interface FireBaseListener {
        void onFireBaseChange();
    }

}
