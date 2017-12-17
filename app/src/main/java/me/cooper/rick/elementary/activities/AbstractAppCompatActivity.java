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

    protected static final String PREF_VOL_MUSIC = "pref_volume_music";

    private static final String PREF_VOL_EFFECTS = "pref_volume_effects";
    private static final String PREF_TOG_VIBRATE = "pref_toggle_vibrate";

    private static final String SOUND_CLICK = "click";

    private static final int DEFAULT_VOLUME = 5;

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    private Map<String, Integer> sounds = new HashMap<>();

    protected boolean isFragOpen = false;

    private boolean vibrate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        vibrate = preferences.getBoolean(PREF_TOG_VIBRATE, false);
        preferences.registerOnSharedPreferenceChangeListener(this);

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build())
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
                vibrate = preferences.getBoolean(PREF_TOG_VIBRATE, false);
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

    @Override
    protected void onPause() {
        super.onPause();
        pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMusic();
    }

    protected void initMedia() {
        addSound(SOUND_CLICK, R.raw.click);
    }

    protected void initMusic(int musicId) {
        mediaPlayer = MediaPlayer.create(this, musicId);
        mediaPlayer.setLooping(true);
        onSharedPreferenceChanged(PreferenceManager.getDefaultSharedPreferences(this),
                PREF_VOL_MUSIC);
        startMusic();
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

    protected void displayToastMessage(int stringId, Object... args) {
        displayToastMessage(getString(stringId, (Object[]) args));
    }

    protected void addSound(String key, int rawSoundId) {
        sounds.put(key, soundPool.load(this, rawSoundId, 1));
    }

    protected void playSound(String key) {
        float volume = getVolumeSetting(PreferenceManager.getDefaultSharedPreferences(this),
                PREF_VOL_EFFECTS);

        soundPool.play(sounds.get(key), volume, volume, 1, 0, 1);
    }

    protected void doClickResponse() {
        playSound(SOUND_CLICK);
        vibrate(CLICK);
    }

    protected void vibrate(VibratePattern pattern) {
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(pattern.pattern, -1);
            }
        }
    }

    protected void startFragment(int contentId, Fragment fragment, String tag) {
        isFragOpen = true;
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(contentId, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    protected boolean popFragment() {
        return getSupportFragmentManager().popBackStackImmediate();
    }

    private void controlMediaPlayer(MediaPlayerAction action) {
        if (mediaPlayer != null) {
            action.execute(mediaPlayer);
        }
    }

    private float getVolumeSetting(SharedPreferences preferences, String tag) {
        return preferences.getInt(tag, DEFAULT_VOLUME) / 10.0f;
    }

    private void setMusicVolume(final float volume) {
        controlMediaPlayer(new MediaPlayerAction() {
            @Override
            public void execute(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(volume, volume);
            }
        });
    }

    private void setEffectVolume(String tag, float volume) {
        soundPool.setVolume(sounds.get(tag), volume, volume);
    }

    private void setSoundVolume(float volume) {
        for (String key : sounds.keySet()) {
            setEffectVolume(key, volume);
        }
    }

    private void displayToastMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }

    private interface MediaPlayerAction {
        void execute(MediaPlayer mediaPlayer);
    }

}
