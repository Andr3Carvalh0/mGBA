package io.mgba.UI.Activities.Interfaces;

import android.support.v7.app.AppCompatActivity;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.mikepenz.aboutlibraries.LibsBuilder;
import java.util.List;
import io.mgba.Data.Database.Game;

public interface IMainView {
    void clearSuggestions();
    void showSuggestions(List<? extends SearchSuggestion> list);
    void startAboutPanel(LibsBuilder aboutPanel);
    void startActivityForResult(Class<? extends AppCompatActivity> activity, int code);
    void showProgress();
}
