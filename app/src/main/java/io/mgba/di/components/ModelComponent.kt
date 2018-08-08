package io.mgba.di.components

import javax.inject.Singleton

import dagger.Component
import io.mgba.di.modules.ModelModule
import io.mgba.model.Library
import io.mgba.presenter.IntroPresenter
import io.mgba.presenter.MainPresenter

@Singleton
@Component(modules = [ModelModule::class])
interface ModelComponent {
    fun inject(target: IntroPresenter)
    fun inject(target: MainPresenter)
    fun inject(library: Library)
}
