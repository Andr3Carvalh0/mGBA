package io.mgba.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.mgba.widgets.MaterialSnackbar

/**
 * Created by André Carvalho on 21/08/2018
 */
abstract class BaseFragment<T : ViewModel> : Fragment() {

    protected lateinit var vm: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = createViewModel()
        return inflater.inflate(getLayout(), container, false)
    }

    abstract fun getLayout(): Int
    abstract fun createViewModel(): T

    fun showSnackbarAlert(view: View, text: String) {
        MaterialSnackbar.notify(view)
                .title(text)
                .build()
                .show()
    }

    fun showSnackbarAlert(view: View, text: String, onClickText: String, onClick: (View) -> Unit) {
        MaterialSnackbar.notify(view)
                .title(text)
                .onClick(onClickText, onClick)
                .build()
                .show()
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}


