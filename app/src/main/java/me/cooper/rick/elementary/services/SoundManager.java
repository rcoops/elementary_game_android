package me.cooper.rick.elementary.services;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

    private static SoundManager instance;

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }



}
