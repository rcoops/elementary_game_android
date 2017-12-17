package me.cooper.rick.elementary.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.constants.VibratePattern;
import me.cooper.rick.elementary.fragments.InstructionsFragment;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static me.cooper.rick.elementary.constants.VibratePattern.CLICK;

public abstract class AbstractAppCompatActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        InstructionsFragment.OnFragmentInteractionListener,
        HighScoreFragment.OnFragmentInteractionListener {

    protected static final String SOUND_DRAWER = "drawer";
    protected static final String PREF_VOL_MUSIC = "pref_volume_music";

    private static final String PREF_VOL_EFFECTS = "pref_volume_effects";
    private static final String PREF_TOG_VIBRATE = "pref_toggle_vibrate";

    private static final String SOUND_CLICK = "click";

    private static final int DEFAULT_VOLUME = 5;

    private SharedPreferences preferences;
    private FragmentManager fragmentManager;
    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private Vibrator vibrator;

    private Map<String, Integer> sounds = new HashMap<>();

    protected boolean isFragOpen = false;

    private boolean vibrate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        vibrate = preferences.getBoolean(PREF_TOG_VIBRATE, true);

        preferences.registerOnSharedPreferenceChangeListener(this);

        fragmentManager = getSupportFragmentManager();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        switch (key) {
            case PREF_VOL_MUSIC:
                setMusicVolume(getVolumeSetting(preferences, key));
                break;
            case PREF_VOL_EFFECTS:
                setSoundVolume(getVolumeSetting(preferences, key));
                playSound(SOUND_CLICK);
                break;
            case PREF_TOG_VIBRATE:
                vibrate = preferences.getBoolean(PREF_TOG_VIBRATE, true);
                vibrate(CLICK);
                break;
        }
    }

    @Override
    public void onFragmentInteraction() {
        isFragOpen = false;
        doClickResponse();
        onBackPressed();
    }

    protected void initMedia() {
        addSound(SOUND_CLICK, R.raw.click);
    }

    protected void initMusic(int musicId) {
        mediaPlayer = MediaPlayer.create(this, musicId);
        mediaPlayer.setLooping(true);
        onSharedPreferenceChanged(preferences, PREF_VOL_MUSIC);
        startMusic();
    }

    private void controlMediaPlayer(MediaPlayerAction action) {
        if (mediaPlayer != null) {
            action.execute(mediaPlayer);
        }
    }

    protected void startMusic() {
        controlMediaPlayer(new MediaPlayerAction() {
            @Override
            public void execute(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    protected void pauseMusic() {
        controlMediaPlayer(new MediaPlayerAction() {
            @Override
            public void execute(MediaPlayer mediaPlayer) {
                mediaPlayer.pause();
            }
        });
    }

    protected void stopMusic() {
        controlMediaPlayer(new MediaPlayerAction() {
            @Override
            public void execute(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    protected void displayToastMessage(int stringId, Object... args) {
        displayToastMessage(getString(stringId, (Object[]) args));
    }

    protected void addSound(String key, int rawSoundId) {
        sounds.put(key, soundPool.load(this, rawSoundId, 1));
    }

    protected void playSound(String key) {
        float volume = getVolumeSetting(preferences, PREF_VOL_EFFECTS);

        soundPool.play(sounds.get(key), volume, volume, 1, 0, 1);
    }

    protected void doClickResponse() {
        playSound(SOUND_CLICK);
        vibrate(CLICK);
    }

    protected void vibrate(VibratePattern pattern) {
        if (vibrate) {
            vibrator.vibrate(pattern.pattern, -1);
        }
    }

    protected void startFragment(int contentId, Fragment fragment, String tag) {
        isFragOpen = true;
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .add(contentId, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    protected boolean popFragment() {
        return fragmentManager.popBackStackImmediate();
    }

    private void displayToastMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }

    private void setEffectVolume(String tag, float volume) {
        soundPool.setVolume(sounds.get(tag), volume, volume);
    }

    private void setSoundVolume(float volume) {
        for (String key : sounds.keySet()) {
            setEffectVolume(key, volume);
        }
    }

    private void setMusicVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    private float getVolumeSetting(SharedPreferences preferences, String tag) {
        return preferences.getInt(tag, DEFAULT_VOLUME) / 10.0f;
    }

    private interface MediaPlayerAction {

        void execute(MediaPlayer mediaPlayer);

    }

}
