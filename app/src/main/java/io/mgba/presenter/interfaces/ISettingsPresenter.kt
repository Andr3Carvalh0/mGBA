package io.mgba.presenter.interfaces

import io.mgba.data.settings.Settings

interface ISettingsPresenter {

    fun getSettings(): List<Settings>
    fun onClick(game: Settings) : Any
}
