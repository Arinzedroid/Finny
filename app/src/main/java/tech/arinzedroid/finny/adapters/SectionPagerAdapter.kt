package tech.arinzedroid.finny.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import tech.arinzedroid.finny.fragments.*

class SectionPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> GoalsListFragment.newInstance()
            1 -> ExpenseListFragment.newInstance()
            2 -> SavingsListFragment.newInstance()
            3 -> RevenuesListFragment.newInstance()
            else -> {
               PlaceHolderFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 6
    }
}