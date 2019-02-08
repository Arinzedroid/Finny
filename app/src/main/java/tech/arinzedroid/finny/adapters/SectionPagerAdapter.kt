package tech.arinzedroid.finny.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import tech.arinzedroid.finny.fragments.GoalsListFragment
import tech.arinzedroid.finny.fragments.RevenuesListFragment

class SectionPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0,1,2,3,4 -> GoalsListFragment.newInstance("", "")
            5 -> RevenuesListFragment.newInstance()
            else -> {
                GoalsListFragment.newInstance("", "")
            }
        }
    }

    override fun getCount(): Int {
        return 6
    }
}