package com.example.hi_ponic.view.monitoring

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.hi_ponic.R
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.databinding.ActivityDetailHydroponicStatisticBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.mainView.MainActivity
import com.example.hi_ponic.view.mainView.SectionPagerAdapter
import com.example.hi_ponic.view.monitoring.view_model.HomeMonitoringViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailHydroponicStatisticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHydroponicStatisticBinding

    private val viewModel by viewModels<HomeMonitoringViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi binding
        binding = ActivityDetailHydroponicStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setTabLayout()
        pageLayout()
        topAppbarHandle()
        setData()
    }

    private fun setData() {
        val tanaman = intent.getStringExtra(EXTRA_TUMBUHAN)
        val tanggal = intent.getStringExtra(EXTRA_TANGGAL)

        binding.TanggalTanam.text = "Tanggal Tanam : ${formatDateString(tanggal.toString())}"
        binding.tvTumbuhan.text = tanaman
    }

    private fun formatDateString(dateString: String): String {
        return try {
            val inputFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }

    private fun topAppbarHandle() {
        binding.topAppbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.topAppbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    AlertDialog.Builder(this).apply {
                        setTitle("Delete Plant")
                        setMessage("Are you sure to delete this plant?")
                        setPositiveButton("OK") { _, _ ->
                            val id = intent.getIntExtra(EXTRA_ID,0)
                            viewModel.deletePlant(id)
                            viewModel.isError.observe(this@DetailHydroponicStatisticActivity){
                                if (it.isNotEmpty()){
                                    Toast.makeText(this@DetailHydroponicStatisticActivity, "Plant deleted successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@DetailHydroponicStatisticActivity,MainActivity::class.java)
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(this@DetailHydroponicStatisticActivity, it, Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                        setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                    true
                }
                else -> false
            }
        }
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

        const val EXTRA_ID="extra_id"
        const val EXTRA_TUMBUHAN = "extra_tumbuhan"
        const val EXTRA_TANGGAL = "extra_tanggal"
    }
}
