package io.mgba.DI.Components;

import javax.inject.Singleton;

import dagger.Component;
import io.mgba.DI.Modules.ModelModule;
import io.mgba.Model.Library;
import io.mgba.Presenter.IntroPresenter;
import io.mgba.Presenter.MainPresenter;

@Singleton
@Component(modules = ModelModule.class)
public interface ModelComponent {
    void inject(IntroPresenter target);
    void inject(MainPresenter target);
    void inject(Library library);
}
