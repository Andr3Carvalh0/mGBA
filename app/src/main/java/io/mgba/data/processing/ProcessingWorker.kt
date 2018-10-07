package io.mgba.data.processing

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.mgba.data.Library
import java.lang.Exception

class ProcessingWorker(context : Context, params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val id = inputData.getString(GAME_ID)

        if(id.isNullOrEmpty()) { return Result.FAILURE }

        return try {
            id?.let {
                Library.populateGame(it)
                Result.SUCCESS
            }

            Result.FAILURE
        }catch (e: Exception) {
            Result.FAILURE
        }
    }


    companion object {
        const val GAME_ID = "game_id"
    }
}
