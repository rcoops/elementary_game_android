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

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.exit;
import static me.cooper.rick.elementary.constants.Constants.DEFAULT_VOLUME;
import static me.cooper.rick.elementary.constants.Constants.PREF_TOG_VIBRATE;
import static me.cooper.rick.elementary.constants.Constants.PREF_VOL_EFFECTS;
import static me.cooper.rick.elementary.constants.Constants.PREF_VOL_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.SOUND_CLICK;


public abstract class AbstractAppCompatActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    protected FragmentManager fragmentManager;
    protected SharedPreferences preferences;

    protected SoundPool soundPool;
    protected MediaPlayer mediaPlayer;

    private Vibrator vibrator;

    private boolean vibrate;
    protected boolean isFragOpen = false;

    protected Map<String, Integer> sounds = new HashMap<>();

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

    protected void initMedia() {
        sounds.put(SOUND_CLICK, soundPool.load(this, R.raw.click, 1));
    }

    protected void displayToastMessage(int stringId, Object... args) {
        displayToastMessage(getString(stringId, (Object[]) args));
    }

    protected void displayToastMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }

    protected float getVolumeSetting(SharedPreferences preferences, String tag) {
        return preferences.getInt(tag, DEFAULT_VOLUME) / 10f;
    }

    protected void exitApplication() {
        finish();
        exit(0);
    }

    protected void playSound(String key) {
        float volume = getVolumeSetting(preferences, PREF_VOL_EFFECTS);

        soundPool.play(sounds.get(key), volume, volume, 1, 0, 1);
    }

    private void setEffectVolume(String tag, float volume) {
        soundPool.setVolume(sounds.get(tag), volume, volume);
    }

    protected void setSoundVolume(float volume) {
        for (String key : sounds.keySet()) {
            setEffectVolume(key, volume);
        }
    }

    protected void setMusicVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        switch (key) {
            case PREF_VOL_MUSIC:
                setMusicVolume(getVolumeSetting(preferences, key));
            case PREF_VOL_EFFECTS:
                setSoundVolume(getVolumeSetting(preferences, key));
                playSound(SOUND_CLICK);
                break;
            case PREF_TOG_VIBRATE:
                vibrate = preferences.getBoolean(PREF_TOG_VIBRATE, true);
                break;
        }
    }

}
