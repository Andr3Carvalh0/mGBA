package io.mgba.presenter


import android.app.Activity
import android.content.Intent
import com.annimon.stream.Stream
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.github.paolorotolo.appintro.AppIntroFragment
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils
import java.util.LinkedList
import io.mgba.presenter.interfaces.IIntroPresenter
import io.mgba.model.interfaces.IPermissionManager
import io.mgba.model.system.PermissionManager
import io.mgba.model.system.PreferencesManager
import io.mgba.R
import io.mgba.ui.activities.interfaces.IIntroView
import io.mgba.ui.activities.MainActivity
import io.mgba.mgba
import io.mgba.model.Library
import io.mgba.utilities.ResourcesManager.getColor
import io.mgba.utilities.ResourcesManager.getString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import permissions.dispatcher.PermissionRequest

class IntroPresenter(private val permissionService: IPermissionManager,
                     private val view: IIntroView) : IIntroPresenter {

    private val disposable = CompositeDisposable()
    private var isVisible = true
    private var isDone = false

    private val introFragments: Stream<AppIntroFragment>
        get() {
            val slides = LinkedList<AppIntroFragment>()
            slides.add(AppIntro2Fragment.newInstance(
                    getString(R.string.Welcome_Title),
                    getString(R.string.Welcome_Description),
                    R.mipmap.ic_launcher,
                    getColor(R.color.colorPrimary)))
            slides.add(AppIntro2Fragment.newInstance(
                    getString(R.string.Library_Title),
                    getString(R.string.Library_Description),
                    R.mipmap.ic_launcher,
                    getColor(R.color.colorPrimary)))

            return Stream.of(slides)
        }

    init { view.addSlides(introFragments.toList()) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            mgba.printLog(TAG, "received result from activity")
            onEnd(FilePickerUtils.getSelectedDir(intent))
        }
    }

    override fun onDeniedForStorage() {
        onEnd()
    }

    override fun showFilePicker() {
        permissionService.showFilePicker()
    }

    override fun showRationaleForStorage(request: PermissionRequest) {
        permissionService.showRationaleForStorage(request)
    }

    override fun onResume() {
        isVisible = true

        if (isDone) {
            mgba.printLog(TAG, "Finishing setup coming from the background")
            onEnd()
        }
    }

    override fun onPause() {
        isVisible = false
    }

    // Since this Single object runs outside the UI Thread, we will leave it be.
    // In case of a onPause event, we will not terminate it. It will do want it need and when finished,
    // set the flag isDone.
    // In the eventual case that the app isnt killed, when the user comes back, the onResume will be triggered,
    // And we will continue to the MainActivity
    // If thats not the case, and the app ends up dead, there will be no problem(*) and the app will start the main activity
    // * There could also happen that the app gets kill in between the processing...
    //  In that case the flag setup_done will stay false and on the next launch the setup will show up again.
    private fun onEnd(dir: String) {
        view.savePreference(PreferencesManager.GAMES_DIRECTORY, dir)
        view.showProgressDialog()

        disposable.add(Library.reloadGames(dir)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    isDone = true
                    onEnd()
                })
    }

    private fun onEnd() {
        view.savePreference(PreferencesManager.SETUP_DONE, true)
        view.showProgressDialog()

        //House cleaning
        if (!disposable.isDisposed) disposable.dispose()

        if (isVisible)
            view.startActivity(MainActivity::class.java)
    }

    companion object {
        private val TAG = "mgba:IntroCtr"
    }
}
