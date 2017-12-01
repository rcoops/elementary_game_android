package me.cooper.rick.elementary.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.cooper.rick.elementary.activity.fragment.game.GameFragment;
import me.cooper.rick.elementary.activity.fragment.NewPlayerFragment;
import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activity.fragment.score.ScoreFragment;
import me.cooper.rick.elementary.activity.fragment.score.content.ScoreContent;
import me.cooper.rick.elementary.models.Player;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewPlayerFragment.OnPlayerCreatedListener,
        GameFragment.OnFragmentInteractionListener,
        ScoreFragment.OnListFragmentInteractionListener {

    private Player player;

    private FragmentManager fragmentManager;
    private Fragment newPlayerFragment;
    private Fragment scoreFragment;
    private GameFragment gameFragment;

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
        newPlayerFragment = new NewPlayerFragment();
        scoreFragment = new ScoreFragment();
        gameFragment = new GameFragment();
        gameFragment.setHasOptionsMenu(true);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_new_game:
                startFragment(R.id.dialog_layout, newPlayerFragment);
                break;
            case R.id.nav_scores:
                startFragment(R.id.content_main, scoreFragment);
                break;
            case R.id.nav_quit:
                exitApplication();
            default:
                displayToastMessage(R.string.err_not_implemented); // Won't happen
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startFragment(int contentId, Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(contentId, fragment)
                    .commit();
        }
    }

    @Override
    public void onPlayerCreated(Player player) {
        gameFragment.setPlayer(player);
        this.player = player;
        displayToastMessage("Welcome: " + player.getPlayerName() + "!");
        startFragment(R.id.content_main, gameFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(ScoreContent.ScoreItem item) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void exitApplication() {
        finish();
        exit(0);
    }

    private void displayToastMessage(int stringId) {
        displayToastMessage(getString(stringId));
    }

    private void displayToastMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }

    private void tostOnMain(){
        gameFragment.getHandler().post(new Runnable() {
            @Override
            public void run() {
                gameFragment.makeToast("from other thread");
            }
        });
    }

}
