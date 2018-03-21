package io.mgba.UI.Activities.Interfaces;

import android.support.v7.app.AppCompatActivity;

import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.List;


public interface IIntroView {
    void addSlides(List<AppIntroFragment> slides);
    void savePreference(String key, String value);
    void savePreference(String key, boolean value);
    void showProgressDialog();
    void startActivity(Class<? extends AppCompatActivity> activity);
}
