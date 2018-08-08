package io.mgba.ui.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import java.util.Objects
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import io.mgba.Constants
import io.mgba.model.system.PermissionManager
import io.mgba.presenter.interfaces.ISettingsPanelPresenter
import io.mgba.presenter.SettingsPanelPresenter
import io.mgba.R
import io.mgba.ui.activities.interfaces.ISettings
import io.mgba.ui.activities.interfaces.ISettingsPanelView
import io.mgba.utilities.IResourcesManager
import io.mgba.mgba
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class SettingsPanelActivity : AppCompatActivity(), ISettings, ISettingsPanelView {
    private var controller: ISettingsPanelPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_panel)

        val id = if (savedInstanceState == null)
            Objects.requireNonNull(intent.extras).getString(Constants.ARG_SETTINGS_ID)
        else
            savedInstanceState.getString(Constants.ARG_SETTINGS_ID)


        controller = SettingsPanelPresenter(PermissionManager(this), this,
                id, application as IResourcesManager)

        setupFragment()
        setupToolbar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controller!!.onSaveInstance(outState)
        super.onSaveInstanceState(outState)
    }

    private fun setupToolbar() {
        if (supportActionBar != null) {
            supportActionBar!!.title = controller!!.title
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupFragment() {
        controller!!.setupFragment()
    }

    override fun requestPreferencesValue(key: String, defaultValue: String): String {
        return controller!!.requestPreferencesValue(key, defaultValue)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun showFilePicker() {
        controller!!.showFilePicker()
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun showRationaleForStorage(request: PermissionRequest) {
        controller!!.showRationaleForStorage(request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        controller!!.onActivityResult(requestCode, resultCode, intent!!)
    }

    override fun requestStoragePermission() {
      //  SettingsPanelActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this)
    }
/*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        SettingsPanelActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }
*/
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun findFragment(id: String): PreferenceFragmentCompat {
        return supportFragmentManager.findFragmentByTag(TAG + id) as PreferenceFragmentCompat
    }

    override fun switchFragment(fragment: PreferenceFragmentCompat, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, fragment, tag)
                .commit()

    }

    override fun getPreference(key: String, defaultValue: String): String {
        return (application as mgba).getPreference(key, defaultValue)
    }

    companion object {

        private val TAG = "Storage_Fragment"
    }
}