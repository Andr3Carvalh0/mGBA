package io.mgba.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.mgba.model.system.PreferencesManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hasDoneSetup = PreferencesManager[PreferencesManager.SETUP_DONE, false]

        val it = Intent(this, if (hasDoneSetup) MainActivity::class.java else IntroActivity::class.java)
        startActivity(it)
        finish()
    }

}
