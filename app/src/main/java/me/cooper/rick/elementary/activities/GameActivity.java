package me.cooper.rick.elementary.activities;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
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
import me.cooper.rick.elementary.fragments.QuitGameFragment;
import me.cooper.rick.elementary.fragments.SettingsFragment;
import me.cooper.rick.elementary.listeners.ShakeListener;
import me.cooper.rick.elementary.models.Player;
import me.cooper.rick.elementary.models.view.ChemicalSymbolView;
import me.cooper.rick.elementary.models.view.ElementAnswerView;
import me.cooper.rick.elementary.services.FireBaseManager;
import me.cooper.rick.elementary.services.QuizManager;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static me.cooper.rick.elementary.constants.VibratePattern.CORRECT;
import static me.cooper.rick.elementary.constants.VibratePattern.QUIT;
import static me.cooper.rick.elementary.constants.VibratePattern.WRONG;
import static me.cooper.rick.elementary.models.Player.PLAYER_INTENT_TAG;
import static me.cooper.rick.elementary.services.QuizManager.NO_OF_ELEMENTS;

public class GameActivity extends AbstractAppCompatActivity implements Runnable,
        QuitGameFragment.OnFragmentInteractionListener,
        ShakeListener.OnShakeListener {

    private static final String SOUND_WRONG = "wrong";
    private static final String SOUND_GAME_OVER = "game_over";
    private static final String SOUND_RIGHT = "correct";

    private RelativeLayout content;
    private Point size;
    private Point centre;

    private MenuItem toggleItem;

    private ChemicalSymbolView chemicalSymbolView;
    private List<ElementAnswerView> answerViews = new ArrayList<>();

    private Player player;

    private QuizManager quizManager = QuizManager.getInstance();
    private FireBaseManager fireBaseManager = FireBaseManager.getInstance();

    private Thread thread;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        player = getIntent().getParcelableExtra(PLAYER_INTENT_TAG);

        disableKeyboard();
        setViewReferences();
        setViewSize();
        addChemicalSymbolView();
        initMovementSensors();
        resetTitle();
        initViews();
        initThread();
        initMedia();

        displayToastMessage(R.string.txt_welcome_game, player.getPlayerName());

        startFragment(R.id.game_space, new InstructionsFragment(), InstructionsFragment.TAG);
    }

    @Override
    protected void initMedia() {
        super.initMedia();

        addSound(SOUND_WRONG, R.raw.negative);
        addSound(SOUND_GAME_OVER, R.raw.negative_2);
        addSound(SOUND_RIGHT, R.raw.positive);

        initMusic(R.raw.game_music);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        onPause();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        if (!isFragOpen && isRunning) {
            onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        toggleItem = menu.findItem(R.id.nav_toggle_control);
        setToggleMenuText(chemicalSymbolView.getStrategyDescription()); // Must go after sensorManager
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        chemicalSymbolView.stopMoving();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        chemicalSymbolView.startMoving();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        doClickResponse();
        switch (item.getItemId()) {
            case R.id.nav_toggle_control:
                onShake();
                onResume();
                break;
            case R.id.nav_quit:
                startFragment(R.id.game_space, new QuitGameFragment(), QuitGameFragment.TAG);
                break;
            case R.id.nav_instructions:
                startFragment(R.id.game_space, new InstructionsFragment(), InstructionsFragment.TAG);
                break;
            case R.id.nav_game_settings:
                startFragment(R.id.game_space, new SettingsFragment(), SettingsFragment.TAG);
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
                isRunning = false;
                chemicalSymbolView.stopMoving();
                chemicalSymbolView.resetPosition();
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
        if (!popFragment()) {
            startFragment(R.id.game_space, new QuitGameFragment(), QuitGameFragment.TAG);
        } else {
            isFragOpen = false;
            onResume();
        }
    }

    @Override
    public void onFragmentInteraction(boolean isQuitConfirmed) {
        if (isQuitConfirmed) {
            exit();
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onShake() {
        String nextStrategyDescription = chemicalSymbolView.nextStrategy();
        setToggleMenuText(nextStrategyDescription);
    }

    private void setToggleMenuText(String nextStrategyDescription) {
        String newTitle = getString(R.string.action_toggle_control, nextStrategyDescription);
        toggleItem.setTitle(newTitle);
    }

    private void resetUI(final boolean correctAnswer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playSound(correctAnswer ? SOUND_RIGHT : SOUND_WRONG);
                displayToastMessage(correctAnswer ? R.string.txt_correct_answer : R.string.txt_wrong_answer);
                resetViews();
            }
        });
    }

    private void initMovementSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(new ShakeListener(this), sensor,
                    SENSOR_DELAY_GAME);
        }
    }

    private void disableKeyboard() {
        if (content != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(content.getWindowToken(), 0);
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

    private void exit() {
        chemicalSymbolView.stopMoving();
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
        content = findViewById(R.id.game_space);

        setAnswerViewReferences(R.id.answer_top, R.id.answer_bottom, R.id.answer_left,
                R.id.answer_right);
    }

    private void setAnswerViewReferences(int... ids) {
        for (int id : ids) {
            answerViews.add((ElementAnswerView) findViewById(id));
        }
    }

    private void resetTitle() {
        setTitle("");
        TextView textView = findViewById(R.id.txt_title_left);
        textView.setText(getString(R.string.txt_title_game_left, player.getScore()));
        textView = findViewById(R.id.txt_title_right);
        textView.setText(getString(R.string.txt_title_game_right, player.getLives()));
    }

    private void initViews() {
        resetTitle();
        quizManager.resetAnswers();
        initChemicalSymbolView();
        initAnswerViews();
    }

    private void resetViews() {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        initViews();
        initThread();
        chemicalSymbolView.startMoving();
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

        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
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

}
