package me.cooper.rick.elementary.activities.game;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activities.AbstractAppCompatActivity;
import me.cooper.rick.elementary.activities.game.util.MovementManager;
import me.cooper.rick.elementary.activities.game.util.QuizManager;
import me.cooper.rick.elementary.listeners.AcceleroListener;
import me.cooper.rick.elementary.models.Player;
import me.cooper.rick.elementary.models.view.ChemicalSymbolView;
import me.cooper.rick.elementary.models.view.ElementAnswerView;

import static me.cooper.rick.elementary.constants.Constants.PLAYER_INTENT_TAG;
import static me.cooper.rick.elementary.constants.Constants.SCORES_DB;
import static me.cooper.rick.elementary.constants.Constants.VIBRATE_CORRECT;
import static me.cooper.rick.elementary.constants.Constants.VIBRATE_QUIT;
import static me.cooper.rick.elementary.constants.Constants.VIBRATE_WRONG;

public class GameActivity extends AbstractAppCompatActivity implements Runnable {

    private Vibrator vibrator;

    private RelativeLayout content;
    private Point size = new Point();
    private Point centre;

    private TextView titleLeft;
    private TextView titleRight;
    private MenuItem toggleItem;
    private ChemicalSymbolView chemicalSymbolView;
    private List<ElementAnswerView> answerViews = new ArrayList<>();

    private Player player;

    private MovementManager movementManager;
    private QuizManager quizManager = QuizManager.getInstance();

    private Thread thread;
    boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        content = findViewById(R.id.game_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        player = getIntent().getParcelableExtra(PLAYER_INTENT_TAG);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        disableKeyboard();
        setViewReferences();
        setViewSize();
        addChemicalSymbolView();
        initViews();
        setupSensorManager();
        resetTitle();
        initThread();

        displayToastMessage(R.string.txt_welcome_game, player.getPlayerName());
    }

    private void setupSensorManager() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            movementManager = new MovementManager(sensorManager, chemicalSymbolView);
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(new ShakeListener(), sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void disableKeyboard() {
        if (content != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
            }
        }
    }

    private void setViewSize() {
        if (size == null || size.x == 0 || size.y == 0) {
            getWindowManager().getDefaultDisplay().getSize(size);
            centre = new Point(size.x / 2, size.y / 2);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        onPause();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        onResume();
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        toggleItem = menu.findItem(R.id.nav_toggle_control);
        setToggleMenuText(); // Must go after sensorManager
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        movementManager.stopMoving();
        vibrator.vibrate(100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        movementManager.startMoving();
        vibrator.vibrate(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_toggle_control:
                movementManager.activateNextMoveStrategy();
                setToggleMenuText();
                break;
            case R.id.nav_quit:
                exit();
                break;
            default:
                displayToastMessage(R.string.err_not_implemented);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        while (isRunning) {
            ElementAnswerView collidedView = checkCollisions();
            if (collidedView != null) {
                movementManager.stopMoving();
                chemicalSymbolView.resetPosition();
                isRunning = false;
                boolean correctAnswer = quizManager.isCorrectAnswer(collidedView.getAnswer());
                if (correctAnswer) {
                    player.adjustForRightAnswer();
                    Log.d("RICK", player.toString());
                    vibrator.vibrate(VIBRATE_CORRECT, -1);
                } else {
                    player.adjustForWrongAnswer();
                    Log.d("RICK", player.toString());
                    if (player.getLives() <= 0) {
                        exit();
                        return;
                    }
                    vibrator.vibrate(VIBRATE_WRONG, -1);
                }
                resetUI(correctAnswer);
            }
        }
    }

    private void setToggleMenuText() {
        String newTitle = getString(R.string.action_toggle_control, movementManager.getMoveMenuItemText());
        toggleItem.setTitle(newTitle);
    }

    private void resetUI(final boolean correctAnswer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resetViews(correctAnswer);
            }
        });
    }

    private void exit() {
        movementManager.stopMoving();
        isRunning = false;
        saveScore();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vibrator.vibrate(VIBRATE_QUIT, -1);
                displayToastMessage(R.string.txt_game_over);
            }
        });
        finish();
    }

    private void saveScore() {
        if (player.hasScore()) {
            String newScore = SCORES_DB.push().getKey();
            SCORES_DB.child(newScore).setValue(player);
        }
    }

    private void addChemicalSymbolView() {
        chemicalSymbolView = new ChemicalSymbolView(this, centre, size);
        content.addView(chemicalSymbolView);
    }

    private void setViewReferences() {
        titleLeft = findViewById(R.id.txt_title_left);
        titleRight = findViewById(R.id.txt_title_right);

        setAnswerViewReferences(R.id.answer_top, R.id.answer_bottom, R.id.answer_left,
                R.id.answer_right);
    }

    private void setAnswerViewReferences(int... ids) {
        for (int id : ids) {
            answerViews.add((ElementAnswerView) findViewById(id));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void resetTitle() {
        setTitle(player);
    }

    public void setTitle(Player player) {
        setTitle("");
        titleLeft.setText(getString(R.string.txt_title_game_left, player.getScore()));
        titleRight.setText(getString(R.string.txt_title_game_right, player.getLives()));
    }

    private void initViews() {
        resetTitle();
        quizManager.resetAnswers();
        initChemicalSymbolView();
        initAnswerViews();
    }

    private void resetViews(boolean correctAnswer) {
        tearDownThread();
        initViews();
        initThread();
        movementManager.startMoving();
        displayAnswerResult(correctAnswer);
    }

    private void tearDownThread() {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayAnswerResult(boolean correctAnswer) {
        if (correctAnswer) {
            displayToastMessage(R.string.txt_title_game_left, player.getScore());
        } else {
            displayToastMessage(R.string.txt_title_game_right, player.getLives());
        }
    }

    private void initThread() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void initChemicalSymbolView() {
        chemicalSymbolView.reset(quizManager.getTargetElement());
    }

    private void initAnswerViews() {
        List<Pair<String, String>> answers = quizManager.getAnswers();
        Collections.shuffle(answers);

        for (int i = 0; i < 4; ++i) {
            answerViews.get(i).setAnswer(answers.get(i));
        }
    }

    private ElementAnswerView checkCollisions() {
        for (ElementAnswerView view : answerViews) {
            if (view.isIntersecting(chemicalSymbolView)) {
                return view;
            }
        }
        return null;
    }

    private final class ShakeListener extends AcceleroListener implements SensorEventListener {

        ShakeListener() {}

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);

            if (event.sensor.getType() == SENSOR_TYPE && isShake()) {
                movementManager.activateNextMoveStrategy();
            }
        }
    }

}
