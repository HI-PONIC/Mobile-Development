package com.example.hi_ponic

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.hi_ponic.databinding.ActivityDetailHydroponicStatisticBinding
import com.example.hi_ponic.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailHydroponicStatisticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHydroponicStatisticBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_hydroponic_statistic)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityDetailHydroponicStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
        pageLayout()
    }

    private fun pageLayout() {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tablayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setTabLayout() {
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.setBackgroundResource(R.drawable.tab_selected_background)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundResource(R.drawable.tab_unselected_background)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Optional: Do something when the same tab is reselected
            }
        })
        for (i in 0 until binding.tablayout.tabCount) {
            val tab = (binding.tablayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(16, 0, 16, 0)
            tab.requestLayout()
        }

        val defaultTab = binding.tablayout.getTabAt(0)
        defaultTab?.select()
        defaultTab?.view?.setBackgroundResource(R.drawable.tab_selected_background)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.statistik,
            R.string.cek
        )
    }
}