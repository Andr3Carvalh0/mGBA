package io.mgba.Components.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.mgba.Controller.PreferencesController;
import io.mgba.R;
import io.mgba.mgba;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(((mgba)getApplication()).getPreference(PreferencesController.SETUP_DONE, false)){
            //For now start the main activity.In the future start game fetching here!
            Intent it = new Intent(getBaseContext(), MainActivity.class);
            startActivity(it);
            return;
        }


    }
}
