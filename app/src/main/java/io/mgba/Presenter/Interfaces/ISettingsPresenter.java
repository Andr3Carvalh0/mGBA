package io.mgba.Presenter.Interfaces;

import java.util.List;
import io.mgba.Data.Settings.Settings;
import io.reactivex.functions.Consumer;

public interface ISettingsPresenter {

    List<Settings> getSettings();
    Consumer<Settings> getOnClick();
}
