package me.cooper.rick.elementary.activities;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
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
import me.cooper.rick.elementary.fragments.InstructionsFragment;
import me.cooper.rick.elementary.fragments.SettingsFragment;
import me.cooper.rick.elementary.listeners.AcceleroListener;
import me.cooper.rick.elementary.models.Player;
import me.cooper.rick.elementary.models.view.ChemicalSymbolView;
import me.cooper.rick.elementary.models.view.ElementAnswerView;
import me.cooper.rick.elementary.services.FireBaseManager;
import me.cooper.rick.elementary.services.QuizManager;
import me.cooper.rick.elementary.services.movement.MovementManager;

import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_INSTRUCTIONS;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SCORES;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SETTINGS;
import static me.cooper.rick.elementary.constants.Constants.PLAYER_INTENT_TAG;
import static me.cooper.rick.elementary.constants.Constants.PREF_VOL_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.SOUND_CLICK;
import static me.cooper.rick.elementary.constants.Constants.SOUND_GAME_OVER;
import static me.cooper.rick.elementary.constants.Constants.SOUND_RIGHT;
import static me.cooper.rick.elementary.constants.Constants.SOUND_WRONG;
import static me.cooper.rick.elementary.constants.VibratePattern.CLICK;
import static me.cooper.rick.elementary.constants.VibratePattern.CORRECT;
import static me.cooper.rick.elementary.constants.VibratePattern.QUIT;
import static me.cooper.rick.elementary.constants.VibratePattern.WRONG;

public class GameActivity extends AbstractAppCompatActivity implements Runnable {

    private RelativeLayout content;
    private Point size;
    private Point centre;

    private TextView titleLeft;
    private TextView titleRight;
    private MenuItem toggleItem;

    private ChemicalSymbolView chemicalSymbolView;
    private List<ElementAnswerView> answerViews = new ArrayList<>();

    private Player player;

    private MovementManager movementManager;
    private QuizManager quizManager = QuizManager.getInstance();
    private FireBaseManager fireBaseManager = FireBaseManager.getInstance();

    private Thread thread;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        content = findViewById(R.id.game_space);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        player = getIntent().getParcelableExtra(PLAYER_INTENT_TAG);

        disableKeyboard();
        setViewReferences();
        setViewSize();
        addChemicalSymbolView();
        initViews();
        setupSensorManager();
        resetTitle();
        initThread();
        displayToastMessage(R.string.txt_welcome_game, player.getPlayerName());

        initMedia();

        startFragment(R.id.game_space, new InstructionsFragment(), FRAG_TAG_INSTRUCTIONS);
    }

    @Override
    protected void initMedia() {
        super.initMedia();
        sounds.put(SOUND_WRONG, soundPool.load(this, R.raw.negative, 1));
        sounds.put(SOUND_GAME_OVER, soundPool.load(this, R.raw.negative_2, 1));
        sounds.put(SOUND_RIGHT, soundPool.load(this, R.raw.positive, 1));

        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        mediaPlayer.setLooping(true);
        setMusicVolume(getVolumeSetting(preferences, PREF_VOL_MUSIC));
        mediaPlayer.start();
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
        if (size == null) {
            size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            centre = new Point(size.x / 2, size.y / 2);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        onPause();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        if (!isFragOpen) {
            onResume();
        }
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
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        movementManager.stopMoving();
        vibrate(CLICK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        mediaPlayer.start();
        movementManager.startMoving();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        playSound(SOUND_CLICK);
        vibrate(CLICK);
        switch (item.getItemId()) {
            case R.id.nav_toggle_control:
                movementManager.activateNextMoveStrategy();
                setToggleMenuText();
                onResume();
                break;
            case R.id.nav_quit:
                exit();
                break;
            case R.id.nav_instructions:
                startFragment(R.id.game_space, new InstructionsFragment(), FRAG_TAG_INSTRUCTIONS);
                break;
            case R.id.nav_game_settings:
                startFragment(R.id.game_space, new SettingsFragment(), FRAG_TAG_SETTINGS);
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
                    Log.d("DEBUG", player.toString());
                    vibrate(CORRECT);
                } else {
                    player.adjustForWrongAnswer();
                    Log.d("DEBUG", player.toString());
                    if (player.getLives() <= 0) {
                        exit();
                        return;
                    }
                    vibrate(WRONG);
                }
                resetUI(correctAnswer);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            exit();
        } else {
            onResume();
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
                playSound(correctAnswer ? SOUND_RIGHT : SOUND_WRONG);
                resetViews(correctAnswer);
            }
        });
    }

    private void exit() {
        mediaPlayer.release();
        mediaPlayer = null;
        movementManager.stopMoving();
        isRunning = false;
        fireBaseManager.saveScore(player);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vibrate(QUIT);
                displayToastMessage(R.string.txt_game_over);
            }
        });
        playSound(SOUND_GAME_OVER);
        finish();
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
            displayToastMessage(R.string.txt_correct_answer);
        } else {
            displayToastMessage(R.string.txt_wrong_answer);
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

        ShakeListener() {
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);

            if (event.sensor.getType() == SENSOR_TYPE && isShake()) {
                movementManager.activateNextMoveStrategy();
                setToggleMenuText();
            }
        }

    }

}
