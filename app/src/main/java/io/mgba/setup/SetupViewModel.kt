package io.mgba.setup

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils
import io.mgba.utilities.PreferencesManager
import io.mgba.main.MainActivity
import io.mgba.mgba
import io.mgba.data.Library
import io.mgba.utilities.permissions.PermissionsManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SetupViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    val permission: MutableLiveData<PermissionsManager.PERMISSION_STATE> = MutableLiveData()



    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        //if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            mgba.printLog(TAG, "received result from activity")
            onEnd(FilePickerUtils.getSelectedDir(intent))
        //}
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
        PreferencesManager.save(PreferencesManager.GAMES_DIRECTORY, dir)
        //view.showProgressDialog()

        disposable.add(Library.reloadGames(dir)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    //isDone = true
                    end()
                })
    }


    fun setPermissionDenied() {
        permission.postValue(PermissionsManager.PERMISSION_STATE.DENIED)
    }

    fun setPermissionGranted() {
        permission.postValue(PermissionsManager.PERMISSION_STATE.GRANTED)
    }

    fun end() {
        PreferencesManager.save(PreferencesManager.SETUP_DONE, true)
        MainActivity.start()
    }

    companion object {
        private val TAG = "mgba:IntroCtr"
    }
}
