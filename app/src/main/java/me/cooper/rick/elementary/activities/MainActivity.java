package me.cooper.rick.elementary.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.constants.VibratePattern;
import me.cooper.rick.elementary.fragments.InstructionsFragment;
import me.cooper.rick.elementary.fragments.SettingsFragment;
import me.cooper.rick.elementary.fragments.NewGameFragment;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;
import me.cooper.rick.elementary.models.Player;

import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_INSTRUCTIONS;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_NEW_PLAYER;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SCORES;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SETTINGS;
import static me.cooper.rick.elementary.constants.Constants.PLAYER_INTENT_TAG;
import static me.cooper.rick.elementary.constants.Constants.PREF_VOL_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.SOUND_CLICK;
import static me.cooper.rick.elementary.constants.Constants.SOUND_DRAWER;

public class MainActivity extends AbstractAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        NewGameFragment.OnPlayerCreatedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMedia();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new SoundActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initMedia() {
        super.initMedia();
        sounds.put(SOUND_DRAWER, soundPool.load(this, R.raw.rollover3, 1));

        mediaPlayer = MediaPlayer.create(this, R.raw.main);
        mediaPlayer.setLooping(true);
        setMusicVolume(getVolumeSetting(preferences, PREF_VOL_MUSIC));
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mediaPlayer.start();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        playSound(SOUND_CLICK);
        vibrate(VibratePattern.CLICK);
        switch (item.getItemId()) {
            case R.id.nav_new_game:
                startFragment(R.id.dialog_layout, new NewGameFragment(), FRAG_TAG_NEW_PLAYER);
                break;
            case R.id.nav_scores:
                startFragment(R.id.content_main, new HighScoreFragment(), FRAG_TAG_SCORES);
                break;
            case R.id.nav_instructions:
                startFragment(R.id.content_main, new InstructionsFragment(), FRAG_TAG_INSTRUCTIONS);
                break;
            case R.id.nav_settings:
                startFragment(R.id.content_main, new SettingsFragment(), FRAG_TAG_SETTINGS);
                break;
            case R.id.nav_quit:
                exitApplication();
            default:
                displayToastMessage(R.string.err_not_implemented); // Shouldn't happen
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPlayerCreated(Player player) {
        fragmentManager.popBackStack();
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(PLAYER_INTENT_TAG, player);
        mediaPlayer.pause();
        startActivity(gameIntent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class SoundActionBarDrawerToggle extends ActionBarDrawerToggle {

        public SoundActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout,
                                          Toolbar toolbar, int openDrawerContentDescRes,
                                          int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes,
                    closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            playSound(SOUND_DRAWER);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            playSound(SOUND_DRAWER);
        }

    }

}
