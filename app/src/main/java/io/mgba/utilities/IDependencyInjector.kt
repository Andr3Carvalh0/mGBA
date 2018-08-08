package io.mgba.utilities

import io.mgba.model.Library
import io.mgba.presenter.IntroPresenter
import io.mgba.presenter.MainPresenter

interface IDependencyInjector {
    fun inject(target: IntroPresenter)
    fun inject(target: MainPresenter)
    fun inject(library: Library)

}
