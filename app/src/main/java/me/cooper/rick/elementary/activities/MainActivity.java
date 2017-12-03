package me.cooper.rick.elementary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activities.game.GameActivity;
import me.cooper.rick.elementary.fragments.NewPlayerFragment;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment;
import me.cooper.rick.elementary.models.Player;
import me.cooper.rick.elementary.models.Score;

import static me.cooper.rick.elementary.constants.Constants.PLAYER_INTENT_TAG;

public class MainActivity extends AbstractAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewPlayerFragment.OnPlayerCreatedListener,
        HighScoreFragment.OnListFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private Fragment newPlayerFragment;
    private Fragment highScoreFragment;


    private List<Score> highScores = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        fragmentManager = getSupportFragmentManager();
        highScoreFragment = new HighScoreFragment();
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
                newPlayerFragment = new NewPlayerFragment();
                startFragment(R.id.dialog_layout, newPlayerFragment);
                break;
            case R.id.nav_scores:
                startFragment(R.id.content_main, highScoreFragment);
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

    private void startFragment(int contentId, Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(contentId, fragment).commit();
        }
    }

    private void endFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        setTitle(getString(R.string.app_name));
    }

    @Override
    public void onPlayerCreated(Player player) {
        endFragment(newPlayerFragment);
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(PLAYER_INTENT_TAG, player);
        startActivity(gameIntent);
    }

    @Override
    public void onListFragmentInteraction(Score item) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
