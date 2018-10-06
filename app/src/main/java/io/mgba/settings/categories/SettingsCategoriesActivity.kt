package io.mgba.settings.categories

import androidx.recyclerview.widget.LinearLayoutManager
import io.mgba.R
import io.mgba.base.BaseActivity
import io.mgba.base.BaseAdapter
import io.mgba.data.settings.Settings
import io.mgba.settings.categories.recyclerView.SettingsAdapter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsCategoriesActivity : BaseActivity<SettingsCategoriesViewModel>(){
    private val settings: List<Settings> = listOf(Settings(getString(R.string.settings_audio), R.drawable.ic_audiotrack),
                                                  Settings(getString(R.string.settings_video), R.drawable.ic_screen),
                                                  Settings(getString(R.string.settings_emulation), R.drawable.ic_gamepad),
                                                  Settings(getString(R.string.settings_bios), R.drawable.ic_memory),
                                                  Settings(getString(R.string.settings_paths), R.drawable.ic_folder),
                                                  Settings(getString(R.string.settings_customization), R.drawable.ic_themes))

    override fun getLayout(): Int = R.layout.activity_settings
    override fun getViewModel(): Class<SettingsCategoriesViewModel> = SettingsCategoriesViewModel::class.java

    override fun onCreate() {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = SettingsAdapter(this) { s -> vm.onClick(s) }
        (list.adapter as BaseAdapter<Settings>).data = ArrayList(settings)
    }

    private fun setupToolbar() {
        supportActionBar?.let {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
