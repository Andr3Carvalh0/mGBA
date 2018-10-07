package io.mgba.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.mgba.data.processing.DirectoryWorker
import io.mgba.utilities.async.WorkManagerWrapper
import io.mgba.utilities.device.PreferencesManager

class SetupViewModel : ViewModel() {

    companion object {
        const val DIRECTORY_CODE = 327
    }

    val onDone: MutableLiveData<Boolean> = MutableLiveData()

    fun handleDirectory(path: String?) {
        path?.let {
            PreferencesManager.save(PreferencesManager.GAMES_DIRECTORY, it)
            WorkManagerWrapper.scheduleToRunWhenConnected(DirectoryWorker::class.java, DirectoryWorker.TAG)
        }

        handleCancellation()
    }

    fun handleCancellation() {
        PreferencesManager.save(PreferencesManager.SETUP_DONE, true)
        onDone.postValue(true)
    }


}
