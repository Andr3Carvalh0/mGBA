package io.mgba.Utils;

import io.mgba.Model.Library;
import io.mgba.Presenter.IntroPresenter;
import io.mgba.Presenter.MainPresenter;

public interface IDependencyInjector {
    void inject(IntroPresenter target);
    void inject(MainPresenter target);
    void inject(Library library);

}
