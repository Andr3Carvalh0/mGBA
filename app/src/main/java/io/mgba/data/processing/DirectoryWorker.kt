package io.mgba.data.processing

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.mgba.data.FileManager
import io.mgba.data.Library
import io.mgba.data.local.model.Game
import io.mgba.utilities.device.PreferencesManager
import io.mgba.utilities.io.calculateMD5
import java.lang.Exception

class DirectoryWorker(context : Context, params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val path = PreferencesManager.get(PreferencesManager.GAMES_DIRECTORY, "")

        if(path.isEmpty()) Result.FAILURE

        return try {
            Library.clear()
            FileManager.path = path
            FileManager.gameList
                       .map { f ->
                           Game(f.absolutePath, FileManager.getPlatform(f)).also {
                               it.mD5 = calculateMD5(f.readBytes())
                           } }
                       .forEach { g -> Library.save(g) }

            Result.SUCCESS
        }catch (e: Exception) {
            Result.FAILURE
        }
    }

    companion object {
        val TAG: String = "LibraryProcessing"
    }
}
