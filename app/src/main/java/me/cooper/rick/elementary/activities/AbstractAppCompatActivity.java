package me.cooper.rick.elementary.activities;

import android.support.v7.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.exit;


public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    protected void displayToastMessage(int stringId, Object... args) {
        displayToastMessage(getString(stringId, (Object[]) args));
    }

    protected void displayToastMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }

    protected void exitApplication() {
        finish();
        exit(0);
    }

}
