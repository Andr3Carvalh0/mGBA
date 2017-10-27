package io.mgba.Controllers.UI.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.LinkedList;
import java.util.List;

import io.mgba.R;
import io.mgba.Services.System.PreferencesService;
import io.mgba.mgba;

public class IntroActivity extends AppIntro2 {

    private List<AppIntroFragment> slides = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Welcome-screen
        slides.add(AppIntro2Fragment.newInstance(getResources().getString(R.string.Welcome_Title),
                getResources().getString(R.string.Welcome_Description),
                R.mipmap.ic_launcher,
                getResources().getColor(R.color.colorPrimary)));

        //Feature-Library
        slides.add(AppIntro2Fragment.newInstance(getResources().getString(R.string.Library_Title),
                getResources().getString(R.string.Library_Description),
                R.mipmap.ic_launcher,
                getResources().getColor(R.color.colorPrimary)));

        askForPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, slides.size());

        for (AppIntroFragment frag : slides) {
            addSlide(frag);
        }

        setProgressButtonEnabled(true);
        setFadeAnimation();
        showSkipButton(false);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        ((mgba)getApplication()).savePreference(PreferencesService.SETUP_DONE, true);

        Intent it = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(it);
        finish();
    }
}
