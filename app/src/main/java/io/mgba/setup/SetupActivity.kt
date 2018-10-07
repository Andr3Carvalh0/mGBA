package io.mgba.setup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import io.mgba.R
import io.mgba.base.BaseActivity
import android.Manifest.permission
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide




class SetupActivity : IntroActivity() {

    lateinit var vm: SetupViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        isFullscreen = true
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SetupViewModel::class.java)

        addSlide(SimpleSlide.Builder()
                .title("")
                .description("")
                .scrollable(false)
                .permission(Manifest.permission.CAMERA)
                .build())

    }


    companion object {
        private const val STORAGE_PERMISSIONS_REQUEST = 1

        fun start(context: Context) { context.startActivity(Intent(context, SetupActivity::class.java)) }
    }
}
