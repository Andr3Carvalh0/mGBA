package io.mgba

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.util.Log
import java.util.LinkedList
import java.util.Locale

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.mgba.di.components.DaggerModelComponent
import io.mgba.di.components.ModelComponent
import io.mgba.di.modules.ModelModule
import io.mgba.model.interfaces.IPreferencesManager
import io.mgba.model.Library
import io.mgba.model.system.PreferencesManager
import io.mgba.presenter.IntroPresenter
import io.mgba.presenter.MainPresenter
import io.mgba.utilities.IDependencyInjector
import io.mgba.utilities.IDeviceManager
import io.mgba.utilities.IResourcesManager

class mgba : Application(), IResourcesManager, IDependencyInjector, IDeviceManager {

    private var modelComponent: ModelComponent? = null
    private var preferencesController: IPreferencesManager? = null

    override
    val deviceLanguage: String
        get() {
            val iso = Locale.getDefault().isO3Language

            return if (SUPPORTED_LANGUAGES.contains(iso)) iso else SUPPORTED_LANGUAGES[0]
        }

    override val isConnectedToWeb: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    private fun initializeModelComponentDagger(application: mgba): ModelComponent {
        return DaggerModelComponent.builder()
                .modelModule(ModelModule(application, application.getPreference(PreferencesManager.GAMES_DIRECTORY, "")))
                .build()
    }

    fun savePreference(key: String, value: String) {
        preparePreferencesController()
        preferencesController!!.save(key, value)
    }

    fun savePreference(key: String, value: Boolean) {
        preparePreferencesController()
        preferencesController!!.save(key, value)
    }

    fun getPreference(key: String, defaultValue: String): String {
        preparePreferencesController()
        return preferencesController!!.get(key, defaultValue)
    }

    fun getPreference(key: String, defaultValue: Boolean): Boolean {
        preparePreferencesController()
        return preferencesController!!.get(key, defaultValue)
    }

    @Synchronized
    private fun preparePreferencesController() {
        if (preferencesController == null)
            preferencesController = PreferencesManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        modelComponent = initializeModelComponentDagger(this)
    }

    fun showProgressDialog(activity: AppCompatActivity) {
        ProgressDialog.show(activity, getString(R.string.Progress_Title), getString(R.string.Progress_desc), true, false)
    }

    fun showDialog(activity: AppCompatActivity, title: String, desc: String, positive_button: String,
                   negative_button: String, positive_click: DialogInterface.OnClickListener,
                   negative_click: DialogInterface.OnClickListener) {

        AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton(positive_button, positive_click)
                .setNegativeButton(negative_button, negative_click)
                .show()
    }

    override fun inject(target: IntroPresenter) {
        modelComponent!!.inject(target)
    }

    override fun inject(target: MainPresenter) {
        modelComponent!!.inject(target)
    }

    override fun inject(library: Library) {
        modelComponent!!.inject(library)
    }

    companion object {

        private val SUPPORTED_LANGUAGES = LinkedList<String>()

        init {
            SUPPORTED_LANGUAGES.add("eng")
        }

        fun report(error: Throwable) {
            if (BuildConfig.DEBUG) {
                Log.e(" --- An error occurred:", error.message)

            }

        }

        fun printLog(tag: String, message: String) {
            if (BuildConfig.DEBUG)
                Log.v(tag, message)
        }

        lateinit var instance: mgba
            private set
    }
}
