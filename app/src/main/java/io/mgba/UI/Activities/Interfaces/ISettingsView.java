package io.mgba.UI.Activities.Interfaces;

import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;

public interface ISettingsView {
    void startActivity(Class<? extends AppCompatActivity> activity, HashMap<String, String> extras);
}
