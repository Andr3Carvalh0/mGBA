package io.mgba.ui.activities

import android.content.Intent
import android.os.Bundle

import java.util.HashMap

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.mgba.adapters.SettingsAdapter
import io.mgba.presenter.SettingsPresenter
import io.mgba.presenter.interfaces.ISettingsPresenter
import io.mgba.R
import io.mgba.ui.activities.interfaces.ISettingsView
import io.mgba.utilities.IResourcesManager
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), ISettingsView {
    private var controller: ISettingsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        controller = SettingsPresenter(this, application as IResourcesManager)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        settins_list.setHasFixedSize(true)
        settins_list.layoutManager = LinearLayoutManager(this)
        settins_list.adapter = SettingsAdapter(controller!!.getSettings(),
                applicationContext,
                {controller!!.onClick(it)},
                settins_list)
    }

    private fun setupToolbar() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun startActivity(activity: Class<out AppCompatActivity>, extras: HashMap<String, String>) {
        val it = Intent(this, activity)

        for (key in extras.keys)
            it.putExtra(key, extras[key])

        startActivity(it)
    }
}
