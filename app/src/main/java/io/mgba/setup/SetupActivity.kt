package io.mgba.setup

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.github.paolorotolo.appintro.AppIntroFragment
import io.mgba.R
import io.mgba.mgba
import io.mgba.utilities.ResourcesManager
import io.mgba.utilities.nonNullObserve
import io.mgba.utilities.permissions.PermissionController
import io.mgba.utilities.permissions.PermissionsManager
import io.mgba.utilities.permissions.PermissionsManager.PERMISSION_STATE.*

class SetupActivity : AppIntro2(), PermissionsManager {

    private lateinit var vm: SetupViewModel
    private val screens: List<AppIntroFragment> = listOf(
            AppIntro2Fragment.newInstance(ResourcesManager.getString(R.string.Welcome_Title),
                                          ResourcesManager.getString(R.string.Welcome_Description),
                                          R.mipmap.ic_launcher, ResourcesManager.getColor(R.color.colorPrimary)),
            AppIntro2Fragment.newInstance(ResourcesManager.getString(R.string.Library_Title),
                                          ResourcesManager.getString(R.string.Library_Description),
                                          R.mipmap.ic_launcher,
                                          ResourcesManager.getColor(R.color.colorPrimary)))

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SetupViewModel::class.java)

        screens.forEach { s -> addSlide(s) }
        this.isProgressButtonEnabled = true
        this.setFadeAnimation()
        this.showSkipButton(false)

        vm.permission.nonNullObserve(this) {
            when(it) {
                GRANTED -> {

                }

                DENIED -> {
                    vm.end()
                }
                else -> {}
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun requestPermission(permission: Array<String>, id: Int) {
        requestPermissions(permission, id)
    }

    override fun onPermissionDenied(id: Int) {
        when (id) { STORAGE_PERMISSIONS_REQUEST -> { vm.setPermissionDenied() } }
    }

    override fun onPermissionAccepted(id: Int) {
        when (id) { STORAGE_PERMISSIONS_REQUEST  -> { vm.setPermissionGranted() } }
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        PermissionController.requestPermissions(this, this,
                arrayOf(PermissionController.Permissions.STORAGE_PERMISSION),
                { vm.setPermissionGranted() }, STORAGE_PERMISSIONS_REQUEST)
    }

    companion object {
        private const val STORAGE_PERMISSIONS_REQUEST = 1

        fun start() {
            val it = Intent(mgba.context, SetupActivity::class.java)
            mgba.context.startActivity(it)
        }
    }
}
