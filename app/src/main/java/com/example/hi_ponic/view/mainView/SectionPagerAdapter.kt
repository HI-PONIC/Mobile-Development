package com.example.hi_ponic.view.mainView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hi_ponic.view.monitoring.CekFragment
import com.example.hi_ponic.view.monitoring.StatistikFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val id: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StatistikFragment.newInstance(id)
            1 -> CekFragment.newInstance(id)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}