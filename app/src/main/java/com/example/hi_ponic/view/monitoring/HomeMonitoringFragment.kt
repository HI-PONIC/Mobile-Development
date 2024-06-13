package com.example.hi_ponic.view.monitoring

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hi_ponic.R
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.pref.dataStore
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
    }

    private fun setName() {
        lifecycleScope.launch {
            val name = userPreference.getName().first()
            binding.tvGreeting.text = getString(R.string.hi,name)
        }
    }

}