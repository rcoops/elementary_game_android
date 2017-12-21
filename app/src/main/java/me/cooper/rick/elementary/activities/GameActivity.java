package me.cooper.rick.elementary.activities;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
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
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;
import me.cooper.rick.elementary.listeners.ShakeListener;
import me.cooper.rick.elementary.models.game.AnswerView;
import me.cooper.rick.elementary.models.game.PlayerView;
import me.cooper.rick.elementary.models.score.Player;
import me.cooper.rick.elementary.services.FirebaseManager;
import me.cooper.rick.elementary.services.QuizManager;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static me.cooper.rick.elementary.constants.VibratePattern.CORRECT;
import static me.cooper.rick.elementary.constants.VibratePattern.QUIT;
import static me.cooper.rick.elementary.constants.VibratePattern.WRONG;
import static me.cooper.rick.elementary.models.score.Player.PLAYER_INTENT_TAG;
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

    private PlayerView playerView;
    private List<AnswerView> answerViews = new ArrayList<>();

    private Player player;

    private QuizManager quizManager = QuizManager.getInstance();
    private FirebaseManager firebaseManager = FirebaseManager.getInstance();

    private Thread thread;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        setSupportActionBar(findViewById(R.id.toolbar));

        player = getIntent().getParcelableExtra(PLAYER_INTENT_TAG);

        init();

        displayToastMessage(R.string.txt_welcome_game, player.getPlayerName());
    }

    private void init() {
        disableKeyboard();
        setViewReferences();
        if (isVersionOrGreater(Build.VERSION_CODES.JELLY_BEAN)) {
            initView16(findViewById(R.id.game_space));
        } else {
            initView15();
        }
        initMovementSensors();
        resetTitle();
        initSoundMedia(R.raw.game_music);
    }

    @Override
    protected void initSoundMedia(int musicRawId) {
        super.initSoundMedia(musicRawId);

        addSound(SOUND_WRONG, R.raw.negative);
        addSound(SOUND_GAME_OVER, R.raw.negative_2);
        addSound(SOUND_RIGHT, R.raw.positive);
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
        onShake();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.stopMoving();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (playerView != null) {
            playerView.startMoving();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        doClickResponse();
        switch (item.getItemId()) {
            case R.id.nav_toggle_control:
                onShake();
                onResume();
                break;
            case R.id.nav_scores:
                startFragment(R.id.game_space, new HighScoreFragment(), HighScoreFragment.TAG);
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
            AnswerView collidedView = checkCollisions();
            if (collidedView != null) {
                isRunning = false;
                playerView.stopMoving();
                playerView.resetPosition();
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
        String nextStrategyDescription = playerView.cycleNextStrategy();
        setToggleMenuText(nextStrategyDescription);
    }

    private void setToggleMenuText(String nextStrategyDescription) {
        String newTitle = getString(R.string.action_toggle_control, nextStrategyDescription);
        toggleItem.setTitle(newTitle);
    }

    private void resetUI(final boolean correctAnswer) {
        runOnUiThread(() -> {
            playSound(correctAnswer ? SOUND_RIGHT : SOUND_WRONG);
            displayToastMessage(correctAnswer ? R.string.txt_correct_answer : R.string.txt_wrong_answer);
            resetViews();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView16(final View view) {
        // https://stackoverflow.com/questions/3779173/determining-the-size-of-an-android-view-at-runtime
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    size = new Point();
                    size.set(view.getWidth(), view.getHeight());
                    centre = new Point(size.x / 2, size.y / 2);
                    // All following have to be done here as they rely on centre being set
                    addChemicalSymbolView();
                    initViews();
                    initThread();

                    startFragment(R.id.game_space, new InstructionsFragment(), InstructionsFragment.TAG);
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void initView15() {
        if (size == null) {
            size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);

            centre = new Point((int) (size.x / 2.0f), (int) (size.y / 2.0f) + getStatusBarHeight());
        }

        addChemicalSymbolView();
        initViews();
        initThread();

        startFragment(R.id.game_space, new InstructionsFragment(), InstructionsFragment.TAG);
    }

    // https://stackoverflow.com/questions/3407256/height-of-status-bar-in-android
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void exit() {
        playerView.stopMoving();
        isRunning = false;
        firebaseManager.saveScore(player);
        runOnUiThread(() -> {
            vibrate(QUIT);
            displayToastMessage(R.string.txt_game_over);
        });
        playSound(SOUND_GAME_OVER);
        finish();
    }

    private void addChemicalSymbolView() {
        playerView = new PlayerView(this, centre, size);
        content.addView(playerView);
    }

    private void setViewReferences() {
        content = findViewById(R.id.game_space);

        setAnswerViewReferences(R.id.answer_top, R.id.answer_bottom, R.id.answer_left,
                R.id.answer_right);
    }

    private void setAnswerViewReferences(int... ids) {
        for (int id : ids) {
            answerViews.add((AnswerView) findViewById(id));
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
        playerView.startMoving();
    }

    private void initThread() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void initChemicalSymbolView() {
        playerView.reset(quizManager.getTargetElement());
    }

    private void initAnswerViews() {
        List<Pair<String, String>> answers = quizManager.getAnswers();
        Collections.shuffle(answers);

        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            answerViews.get(i).setAnswer(answers.get(i));
        }
    }

    private AnswerView checkCollisions() {
        for (AnswerView view : answerViews) {
            if (view.isIntersecting(playerView)) {
                return view;
            }
        }
        return null;
    }

}
