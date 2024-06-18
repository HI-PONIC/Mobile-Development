package com.example.hi_ponic.view.monitoring

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hi_ponic.R
import com.example.hi_ponic.data.adapter.ListLahanAdapter
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.pref.dataStore
import com.example.hi_ponic.data.response.PlantsItem
import com.example.hi_ponic.databinding.FragmentHomeMonitoringBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.monitoring.view_model.HomeMonitoringViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeMonitoringFragment : Fragment() {

    private var _binding: FragmentHomeMonitoringBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference

    private val viewModel by viewModels<HomeMonitoringViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreference = UserPreference.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener{
            val intent = Intent(context,TambahLahanActivity::class.java)
            startActivity(intent)
        }

        setName()
        setList()
    }

    private fun setList() {
        viewModel.getPlant()
        viewModel.plantData.observe(viewLifecycleOwner){data->
            val adapter = ListLahanAdapter()
            adapter.submitList(data.plants)
            adapter.setOnItemClickCallback(object : ListLahanAdapter.OnItemClickCallback{
                override fun OnItemCLicked(data: PlantsItem) {
                    selectedPlant(data)
                }
            })
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.isLoading.observe(viewLifecycleOwner){showLoading(it)}
    }

    private fun selectedPlant(plantsItem: PlantsItem) {
        val toDetail = Intent(requireContext(),DetailHydroponicStatisticActivity::class.java)
        toDetail.putExtra(DetailHydroponicStatisticActivity.EXTRA_TANGGAL, plantsItem.dateAdded)
        toDetail.putExtra(DetailHydroponicStatisticActivity.EXTRA_TUMBUHAN, plantsItem.name)
        toDetail.putExtra(DetailHydroponicStatisticActivity.EXTRA_ID, plantsItem.id)
        toDetail.putExtra(DetailHydroponicStatisticActivity.EXTRA_IMAGE, plantsItem.image)
        startActivity(toDetail)
    }

    private fun setName() {
        lifecycleScope.launch {
            val name = userPreference.getName().first()
            binding.tvGreeting.text = getString(R.string.hi,name)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}