package io.mgba.Controllers.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import io.mgba.Controllers.UI.Activities.Interfaces.LibraryActivity;
import io.mgba.Data.Wrappers.LibraryLists;
import io.mgba.R;
import io.mgba.Services.System.PreferencesService;
import io.mgba.mgba;

public class SplashActivity extends LibraryActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(((mgba)getApplication()).getPreference(PreferencesService.SETUP_DONE, false)){

            if(hasStoragePermission() && ((mgba)getApplication()).getPreference(PreferencesService.GAMES_DIRECTORY, null) != null){
                super.prepareGames(this::onLoadGames);
                setLoading();
            }else{
                //We dont have storage permission.Nothing we can do here :(
                onLoadGames(null);
            }

            return;
        }

        //Start setup
        Intent it = new Intent(getBaseContext(), IntroActivity.class);
        startActivity(it);
        finish();
    }

    private void setLoading() {

    }

    private Void onLoadGames(LibraryLists lists){
        Intent it = new Intent(getBaseContext(), MainActivity.class);

        if(lists != null)
            it.putExtra("games", lists);

        startActivity(it);
        finish();

        return null;
    }
}
