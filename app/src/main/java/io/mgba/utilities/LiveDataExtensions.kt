package io.mgba.utilities

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun <T> TextView.bind(owner: LifecycleOwner, liveData: LiveData<T>) {
    liveData.nonNullObserve(owner) {
        this.text = "$it"
    }
}

fun <T> TextView.bind(owner: LifecycleOwner, liveData: LiveData<T>, format: String) {
    liveData.nonNullObserve(owner) {
        this.text = String.format(format, "$it")
    }
}

fun <T> TextView.bind(owner: LifecycleOwner, liveData: LiveData<T>, process: (T) -> String) {
    liveData.nonNullObserve(owner) {
        this.text = process.invoke(it)
    }
}