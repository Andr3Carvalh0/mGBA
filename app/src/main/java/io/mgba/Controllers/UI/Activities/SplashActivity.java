package io.mgba.Controllers.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import io.mgba.Constants;
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

        if(getIntent().getExtras() != null){
            //Called when we do the setup and choose the game dir.
            //Without this, the FileService will point to the directory null
            final boolean reset = getIntent().getExtras().getBoolean(Constants.SHOULD_RESET);

            if(reset)
                super.libraryController.updateFileServicePath(((mgba)getApplication()).getPreference(PreferencesService.GAMES_DIRECTORY, ""));
        }

        if(((mgba)getApplication()).getPreference(PreferencesService.SETUP_DONE, false)){

            if(hasStoragePermission() && ((mgba)getApplication()).getPreference(PreferencesService.GAMES_DIRECTORY, "") != null){
                super.prepareGames(this::onLoadGames);
            }else{
                //We dont have storage permission.Nothing we can do here :(
                onLoadGames(null);
            }
        }else {
            //Start setup
            Intent it = new Intent(this, IntroActivity.class);
            startActivity(it);
            finish();
        }
    }

    private Void onLoadGames(LibraryLists lists){
        Intent it = new Intent(this, MainActivity.class);

        if(lists != null)
            it.putExtra(Constants.GAMES_INTENT, lists);

        startActivity(it);
        finish();

        return null;
    }
}
