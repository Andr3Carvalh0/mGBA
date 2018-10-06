package io.mgba.emulation

import io.mgba.R
import io.mgba.base.BaseActivity

class EmulationActivity : BaseActivity<EmulationViewModel>() {

    override fun getLayout(): Int = R.layout.activity_emulation
    override fun getViewModel(): Class<EmulationViewModel> = EmulationViewModel::class.java
}
