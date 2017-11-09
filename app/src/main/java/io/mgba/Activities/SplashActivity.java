package io.mgba.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.mgba.Model.System.PreferencesManager;
import io.mgba.mgba;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasDoneSetup = ((mgba)getApplication()).getPreference(PreferencesManager.SETUP_DONE, false);

        Intent it = new Intent(this, hasDoneSetup ? MainActivity.class : IntroActivity.class);
        startActivity(it);
        finish();
    }

}
