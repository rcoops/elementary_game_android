package me.cooper.rick.elementary.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.fragments.SettingsFragment;
import me.cooper.rick.elementary.fragments.newplayer.NewPlayerFragment;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;
import me.cooper.rick.elementary.models.Player;

import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_NEW_PLAYER;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SCORES;
import static me.cooper.rick.elementary.constants.Constants.FRAG_TAG_SETTINGS;
import static me.cooper.rick.elementary.constants.Constants.MAIN_MUSIC;
import static me.cooper.rick.elementary.constants.Constants.PLAYER_INTENT_TAG;
import static me.cooper.rick.elementary.constants.Constants.PREF_VOL_MUSIC;

public class MainActivity extends AbstractAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewPlayerFragment.OnPlayerCreatedListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentManager fragmentManager;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initMedia();
    }

    private void initMedia() {
        sounds.put(MAIN_MUSIC, soundPool.load(this, R.raw.main, 1));
        float volume = getVolumeSetting(preferences, PREF_VOL_MUSIC);
        soundPool.play(sounds.get(MAIN_MUSIC), volume, volume, 1, -1, 1);
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
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_new_game:
                startFragment(R.id.dialog_layout, new NewPlayerFragment(), FRAG_TAG_NEW_PLAYER);
                break;
            case R.id.nav_scores:
                startFragment(R.id.content_main, new HighScoreFragment(), FRAG_TAG_SCORES);
                break;
            case R.id.nav_settings:
                startFragment(R.id.content_main, new SettingsFragment(), FRAG_TAG_SETTINGS);
                break;
            case R.id.nav_quit:
                exitApplication();
            default:
                displayToastMessage(R.string.err_not_implemented); // Shouldn't happen
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startFragment(int contentId, Fragment fragment, String tag) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .add(contentId, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    public void onPlayerCreated(Player player) {
        fragmentManager.popBackStack();
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(PLAYER_INTENT_TAG, player);
        soundPool.stop(sounds.get(MAIN_MUSIC));
        startActivity(gameIntent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        switch (key) {
            case PREF_VOL_MUSIC:
                setVolume(MAIN_MUSIC, getVolumeSetting(preferences, PREF_VOL_MUSIC));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
