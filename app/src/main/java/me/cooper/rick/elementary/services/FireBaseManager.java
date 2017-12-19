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

    private DatabaseReference scoresDb = FirebaseDatabase.getInstance().getReference(DB_NAME);

    private final List<Score> highScores = new ArrayList<>();

    private FireBaseListener scoreFireBaseListener;

    public static FireBaseManager getInstance() {
        if(instance == null) {
            instance = new FireBaseManager();
        }
        return instance;
    }

    private FireBaseManager() {
        scoresDb.orderByChild(SCORE_FIELD_NAME)
                .limitToLast(HIGH_SCORE_ENTRIES_LIMIT)
                .addChildEventListener(new HighScoreChildEventListener());
    }

    public void setHighScoreListListener(FireBaseListener listener) {
        this.scoreFireBaseListener = listener;
    }

    /**
     * Persists a score to the database as long as that score is over 0
     * @param player    the player whose score is being recorded
     */
    public void saveScore(Player player) {
        if (player.hasScore()) {
            String newScore = scoresDb.push().getKey(); // Create new unique 'Score' key
            scoresDb.child(newScore).setValue(player); // set key value as player
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

    /**
     * Notified when the top 20 elements change.
     */
    public interface FireBaseListener {
        void onFireBaseChange();
    }

}
