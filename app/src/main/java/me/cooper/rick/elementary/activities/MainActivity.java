package me.cooper.rick.elementary.activities;

import android.content.Intent;
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
import me.cooper.rick.elementary.fragments.InstructionsFragment;
import me.cooper.rick.elementary.fragments.NewGameFragment;
import me.cooper.rick.elementary.fragments.SettingsFragment;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;
import me.cooper.rick.elementary.models.score.Player;

import static java.lang.System.exit;
import static me.cooper.rick.elementary.models.score.Player.PLAYER_INTENT_TAG;

public class MainActivity extends AbstractAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        NewGameFragment.OnPlayerCreatedListener {

    private static final String SOUND_DRAWER = "drawer";

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMedia();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initMedia() {
        super.initMedia();

        addSound(SOUND_DRAWER, R.raw.rollover3);

        initMusic(R.raw.main);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startMusic();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        doClickResponse();
        switch (item.getItemId()) {
            case R.id.nav_new_game:
                startFragment(R.id.dialog_layout, new NewGameFragment(), NewGameFragment.TAG);
                break;
            case R.id.nav_scores:
                startFragment(R.id.content_main, new HighScoreFragment(), HighScoreFragment.TAG);
                break;
            case R.id.nav_instructions:
                startFragment(R.id.content_main, new InstructionsFragment(), InstructionsFragment.TAG);
                break;
            case R.id.nav_settings:
                startFragment(R.id.content_main, new SettingsFragment(), SettingsFragment.TAG);
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
        popFragment();
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(PLAYER_INTENT_TAG, player);
        startActivity(gameIntent);
    }

    private void exitApplication() {
        finish();
        exit(0);
    }

}
