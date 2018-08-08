package io.mgba.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.mgba.Constants
import io.mgba.ui.fragments.main.FavouritesFragment
import io.mgba.ui.fragments.main.GameFragment
import io.mgba.mgba.Companion.printLog

class TabViewPager(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val platforms = intArrayOf(Constants.PLATFORM_FAVS, Constants.PLATFORM_GBA, Constants.PLATFORM_GBC)

    init {
        printLog(TAG, "CTOR")
    }

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putInt(Constants.ARG_PLATFORM, platforms[position])

        val fragment = if (position == 0) FavouritesFragment() else GameFragment()
        fragment.arguments = args
        return fragment

    }

    override fun getCount(): Int {
        return platforms.size
    }

    companion object {
        private val TAG = "mgba:TabPager"
    }
}
