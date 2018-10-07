package io.mgba.settings.sections

import io.mgba.R
import io.mgba.base.BaseActivity
import io.mgba.utilities.Constants.ARG_SETTINGS_ID

class SettingsPanelActivity : BaseActivity<SettingsSectionViewModel>() {

    override fun getLayout(): Int = R.layout.activity_settings_panel
    override fun getViewModel(): Class<SettingsSectionViewModel> = SettingsSectionViewModel::class.java

    override fun onCreate() {
        super.onCreate()

        val id = intent.extras?.let { it.getString(ARG_SETTINGS_ID) }
        setupToolbar()


    }


    private fun setupToolbar() {
        supportActionBar?.let {
            ///getSupportActionBar().setTitle(controller.getTitle());
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
