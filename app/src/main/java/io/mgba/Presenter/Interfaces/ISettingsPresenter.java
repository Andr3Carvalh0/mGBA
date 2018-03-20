package io.mgba.Presenter.Interfaces;

import java.util.List;
import io.mgba.Data.Settings.SettingsCategory;
import io.reactivex.functions.Consumer;

public interface ISettingsPresenter {

    List<SettingsCategory> getSettings();
    Consumer<SettingsCategory> getOnClick();
}
