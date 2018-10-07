package io.mgba.utilities.async

import androidx.work.*
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequest
import io.mgba.mgba

object WorkManagerWrapper {
    // Added this to fix the tests... In cause of the workManager not working irl. This is to blame
    init { WorkManager.initialize(mgba.context, Configuration.Builder().build()) }

    private var workManager: WorkManager = WorkManager.getInstance()

    fun scheduleToRunWhenConnected(worker: Class<out Worker>, tag: String, replace: Boolean = false) {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val request = OneTimeWorkRequest.Builder(worker)
                .setConstraints(constraints)
                .build()

        val policy = if(replace) ExistingWorkPolicy.REPLACE else ExistingWorkPolicy.KEEP

        workManager.beginUniqueWork(tag, policy, request).enqueue()
    }

    fun scheduleToRunWhenConnectedWithExtras(worker: Class<out Worker>, extras: List<Pair<String, Int>>, tag: String, replace: Boolean = false) {
        val constraints = Constraints.Builder()
                                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                                .build()

        val data = Data.Builder()
        extras.forEach { k -> data.putInt(k.first, k.second) }

        val request = OneTimeWorkRequest.Builder(worker)
                                                           .setConstraints(constraints)
                                                           .setInputData(data.build())
                                                           .build()


        val policy = if(replace) ExistingWorkPolicy.REPLACE else ExistingWorkPolicy.KEEP

        workManager.beginUniqueWork(tag, policy, request).enqueue()

    }
}

