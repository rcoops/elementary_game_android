package me.cooper.rick.elementary.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import me.cooper.rick.elementary.R;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.exit;
import static me.cooper.rick.elementary.constants.Constants.DEFAULT_VOLUME;
import static me.cooper.rick.elementary.constants.Constants.GAME_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.MAIN_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.SOUND_CLICK;
import static me.cooper.rick.elementary.constants.Constants.SOUND_GAME_OVER;
import static me.cooper.rick.elementary.constants.Constants.SOUND_RIGHT;
import static me.cooper.rick.elementary.constants.Constants.SOUND_WRONG;


public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    protected Vibrator vibrator;
    protected SoundPool soundPool;

    protected Map<String, Integer> sounds = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build();
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

    protected void setVolume(String tag, float volume) {
        soundPool.setVolume(sounds.get(tag), volume, volume);
    }

    protected void setSoundVolume(float volume) {
        for (String key : sounds.keySet()) {
            if (!GAME_MUSIC.equals(key) && !MAIN_MUSIC.equals(key)) {
                setVolume(key, volume);
            }
        }
    }

    protected void toggleVibrate(boolean isVibrateOn) {
        if (isVibrateOn) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        } else {
            vibrator = null;
        }
    }

}
