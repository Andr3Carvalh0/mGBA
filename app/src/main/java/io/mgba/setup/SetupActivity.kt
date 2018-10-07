package io.mgba.setup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils
import com.nononsenseapps.filepicker.Views.Activities.FilePickerActivity
import io.mgba.R
import io.mgba.main.MainActivity
import io.mgba.utilities.device.ResourcesManager
import io.mgba.utilities.nonNullObserve

class SetupActivity : AppIntro2() {

    private lateinit var vm: SetupViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SetupViewModel::class.java)

        addSlide(AppIntroFragment.newInstance(SliderPage().also {
                                                    it.title = getString(R.string.welcome_title)
                                                    it.description = getString(R.string.welcome_description)
                                                    it.bgColor = ResourcesManager.getColor(R.color.gameboy_green) }))

        addSlide(AppIntroFragment.newInstance(SliderPage().also {
                                                    it.title = getString(R.string.organize_title)
                                                    it.description = getString(R.string.organize_description)
                                                    it.bgColor = ResourcesManager.getColor(R.color.gameboy_yellow) }))

        addSlide(AppIntroFragment.newInstance(SliderPage().also {
                                                    it.title = getString(R.string.done_title)
                                                    it.description = getString(R.string.done_description)
                                                    //it.imageDrawable = ic_intro_folder
                                                    it.bgColor = ResourcesManager.getColor(R.color.gameboy_blue) }))

        showSkipButton(false)
        isProgressButtonEnabled = false
        setFadeAnimation()

        vm.onDone.nonNullObserve(this) {
            if(it) {
                MainActivity.start(this)
                finish()
            }
        }
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        newFragment?.let {
            val color = (it as AppIntroFragment).defaultBackgroundColor
            colorizeStatusbar(color)
            isProgressButtonEnabled = color == ResourcesManager.getColor(R.color.gameboy_blue)
        }
    }

    override fun onDonePressed(fragment: Fragment) {
        FilePickerActivity.start(this, SetupViewModel.DIRECTORY_CODE)
    }

    private fun colorizeStatusbar(color: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SetupViewModel.DIRECTORY_CODE && resultCode == Activity.RESULT_OK) {
            vm.handleDirectory(FilePickerUtils.getSelectedDir(intent))
        } else {
            vm.handleCancellation()
        }
    }

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, SetupActivity::class.java)) }
    }
}
