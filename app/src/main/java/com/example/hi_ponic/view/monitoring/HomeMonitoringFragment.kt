package com.example.hi_ponic.view.monitoring

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.FragmentHomeMonitoringBinding
import com.example.hi_ponic.databinding.FragmentProfileBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.profile.ProfileViewModel


class HomeMonitoringFragment : Fragment() {

    private var _binding: FragmentHomeMonitoringBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
}