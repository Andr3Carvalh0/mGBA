package io.mgba.setup

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils
import io.mgba.utilities.device.PreferencesManager
import io.mgba.main.MainActivity
import io.mgba.mgba
import io.mgba.data.Library
import io.mgba.utilities.permissions.PermissionsManager

class SetupViewModel : ViewModel() {


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

        /*disposable.add(Library.reloadGames(dir)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    //isDone = true
                    end()
                })*/
    }


}
