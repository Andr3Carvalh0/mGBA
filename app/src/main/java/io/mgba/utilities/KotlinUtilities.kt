package io.mgba.utilities

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

fun runOnBackground(action: () -> Unit, onFinish: () -> Unit) {
    launch {
        async {
            try {
                action.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.await()

        onFinish.invoke()
    }
}

fun runOnBackground(action: () -> Unit) {
    launch {
        async {
            try {
                action.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun runOnMainThread(action: () -> Unit) {
    launch(UI) {
        try {
            action.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun runOnMainThread(action: () -> Unit, onFinish: () -> Unit) {
    launch(UI) {
        try {
            action.invoke()
            onFinish.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun <A>Collection<A>.forEachParallel(f: suspend (A) -> Unit): Unit = runBlocking {
    map { async(CommonPool) { f(it) } }.forEach { it.await() }
}

