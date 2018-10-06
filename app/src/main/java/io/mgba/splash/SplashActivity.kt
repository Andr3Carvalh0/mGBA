package io.mgba.splash

import io.mgba.R
import io.mgba.base.BaseActivity
import io.mgba.main.MainActivity
import io.mgba.setup.SetupActivity
import io.mgba.utilities.PreferencesManager

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getLayout(): Int = R.layout.activity_splash
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onCreate() {
        super.onCreate()

        if(PreferencesManager[PreferencesManager.SETUP_DONE, false]) {
            MainActivity.start(this)
        } else {
            SetupActivity.start(this)
        }

        close()
    }

}
