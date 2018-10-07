package io.mgba.splash

import android.os.Handler
import android.view.View
import io.mgba.R
import io.mgba.base.BaseActivity
import io.mgba.main.MainActivity
import io.mgba.setup.SetupActivity
import io.mgba.utilities.device.PreferencesManager
import io.mgba.utilities.device.PreferencesManager.get

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getLayout(): Int = R.layout.activity_splash
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onCreate() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        Handler().postDelayed({
            if(get(PreferencesManager.SETUP_DONE, false)) { MainActivity.start(this) } else { SetupActivity.start(this) }
            close()

        }, 2000)
    }

}
