package io.mgba.ui.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import io.mgba.model.system.PermissionManager
import io.mgba.presenter.interfaces.IIntroPresenter
import io.mgba.presenter.IntroPresenter
import io.mgba.ui.activities.interfaces.IIntroView
import io.mgba.utilities.IDependencyInjector
import io.mgba.mgba
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class IntroActivity : AppIntro2(), IIntroView {

    private var controller: IIntroPresenter? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = IntroPresenter(PermissionManager(this), mgba.instance as IDependencyInjector, this)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun showFilePicker() {
        controller!!.showFilePicker()
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun showRationaleForStorage(request: PermissionRequest) {
        controller!!.showRationaleForStorage(request)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun onDeniedForStorage() {
        controller!!.onDeniedForStorage()
    }
/*
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        IntroActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        controller!!.onActivityResult(requestCode, resultCode, intent)
    }

    fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        IntroActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this)
    }



    fun onPause() {
        super.onPause()
        controller!!.onPause()
    }

    protected fun onResume() {
        super.onResume()
        controller!!.onResume()
    }
*/
    override fun addSlides(slides: List<AppIntroFragment>) {
        for (frag in slides)
            this.addSlide(frag)

        this.isProgressButtonEnabled = true
        this.setFadeAnimation()
        this.showSkipButton(false)
    }

    override fun savePreference(key: String, value: String) {
        mgba.instance.savePreference(key, value)
    }

    override fun savePreference(key: String, value: Boolean) {
        mgba.instance.savePreference(key, value)
    }

    override fun showProgressDialog() {
        mgba.instance.showProgressDialog(this)
    }

    override fun startActivity(activity: Class<out AppCompatActivity>) {
        val it = Intent(this, activity)
        startActivity(it)
        finish()
    }
}
