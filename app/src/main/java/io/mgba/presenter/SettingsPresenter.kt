package io.mgba.presenter

import java.util.HashMap
import java.util.LinkedList
import io.mgba.Constants
import io.mgba.presenter.interfaces.ISettingsPresenter
import io.mgba.data.settings.Settings
import io.mgba.R
import io.mgba.ui.activities.interfaces.ISettingsView
import io.mgba.ui.activities.SettingsPanelActivity
import io.mgba.utilities.IResourcesManager
import io.reactivex.annotations.NonNull

class SettingsPresenter(@param:NonNull private val view: ISettingsView, @NonNull resourcesManager: IResourcesManager) : ISettingsPresenter {
    lateinit var settings: LinkedList<Settings>

    override fun onClick(settings: Settings): Any {
        val extras = HashMap<String, String>()
        extras[Constants.ARG_SETTINGS_ID] = settings.title

        view.startActivity(SettingsPanelActivity::class.java, extras)

        return Any()
    }

    init {
        settings.add(Settings(resourcesManager.getString(R.string.settings_audio), R.drawable.ic_audiotrack_black_24dp))
        settings.add(Settings(resourcesManager.getString(R.string.settings_video), R.drawable.ic_personal_video_black_24dp))
        settings.add(Settings(resourcesManager.getString(R.string.settings_emulation), R.drawable.ic_gamepad_black_24dp))
        settings.add(Settings(resourcesManager.getString(R.string.settings_bios), R.drawable.ic_memory_black_24dp))
        settings.add(Settings(resourcesManager.getString(R.string.settings_paths), R.drawable.ic_folder_black_24dp))
        settings.add(Settings(resourcesManager.getString(R.string.settings_customization), R.drawable.ic_palette_black_24dp))
    }

    override fun getSettings(): List<Settings> {
        return settings
    }
}
