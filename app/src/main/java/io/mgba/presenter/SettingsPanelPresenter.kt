package io.mgba.presenter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils
import java.util.HashMap
import androidx.preference.PreferenceFragmentCompat
import io.mgba.Constants
import io.mgba.presenter.interfaces.ISettingsPanelPresenter
import io.mgba.model.interfaces.IPermissionManager
import io.mgba.model.system.PermissionManager
import io.mgba.R
import io.mgba.ui.activities.interfaces.ISettingsPanelView
import io.mgba.ui.fragments.settings.AudioFragment
import io.mgba.ui.fragments.settings.BiosFragment
import io.mgba.ui.fragments.settings.EmulationFragment
import io.mgba.ui.fragments.settings.StorageFragment
import io.mgba.ui.fragments.settings.UIFragment
import io.mgba.ui.fragments.settings.VideoFragment
import io.mgba.utilities.IResourcesManager
import io.reactivex.annotations.NonNull
import permissions.dispatcher.PermissionRequest

class SettingsPanelPresenter(@param:NonNull private val permissionService: IPermissionManager, @param:NonNull private val view: ISettingsPanelView,
                             @param:NonNull override val title: String, @NonNull resourcesManager: IResourcesManager) : ISettingsPanelPresenter {

    private val router: HashMap<String, (String) -> PreferenceFragmentCompat> = HashMap()

    init {

        router[resourcesManager.getString(R.string.settings_audio)] = { s -> AudioFragment() }
        router[resourcesManager.getString(R.string.settings_video)] = { s -> VideoFragment() }
        router[resourcesManager.getString(R.string.settings_emulation)] = { s -> EmulationFragment() }
        router[resourcesManager.getString(R.string.settings_bios)] = { s -> BiosFragment() }
        router[resourcesManager.getString(R.string.settings_paths)] = { s -> StorageFragment() }
        router[resourcesManager.getString(R.string.settings_customization)] = { s -> UIFragment() }
    }

    override fun onSaveInstance(outState: Bundle) {
        outState.putString(Constants.ARG_SETTINGS_ID, title)
    }

    override fun setupFragment() {
        var fragment: PreferenceFragmentCompat? = view.findFragment(TAG + title)

        if (fragment == null)
            fragment = router[title]!!.invoke(title)

        view.switchFragment(fragment, TAG + title)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            val dir = FilePickerUtils.getSelectedDir(intent)
            processDirectory(dir)
        }
    }

    override fun showFilePicker() {
        permissionService.showFilePicker()
    }

    override fun showRationaleForStorage(request: PermissionRequest) {
        permissionService.showRationaleForStorage(request)
    }

    override fun requestPreferencesValue(key: String, defaultValue: String): String {
        return view.getPreference(key, defaultValue)
    }

    private fun processDirectory(dir: String) {
        val fragment = view.findFragment(TAG + title) as StorageFragment
        fragment.changeGamesFolderSummary(dir)
    }

    companion object {
        private val TAG = "Settings_Controller"
    }
}
