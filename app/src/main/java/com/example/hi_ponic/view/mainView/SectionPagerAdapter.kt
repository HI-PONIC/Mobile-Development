package com.example.hi_ponic.view.mainView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hi_ponic.view.monitoring.CekFragment
import com.example.hi_ponic.view.monitoring.StatistikFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = StatistikFragment()
            1 -> fragment = CekFragment()
        }
        return fragment as Fragment
    }


}