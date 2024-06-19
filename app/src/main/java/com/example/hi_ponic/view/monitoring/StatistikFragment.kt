package com.example.hi_ponic.view.monitoring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.FragmentStatistikBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.monitoring.view_model.StatistikViewModel

class StatistikFragment : Fragment() {
    private lateinit var binding: FragmentStatistikBinding

    private val viewModel by viewModels<StatistikViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatistikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(ARG_ID) ?: 0
        viewModel.setId(id) // Update ViewModel with the ID

        viewModel.dataStatistik.observe(viewLifecycleOwner) { data ->
            val kelembapan = data.humidity.toString()
            val suhu = data.temp.toString()
            val tds = data.tds.toString()
            val ph = data.ph.toString()

            binding.tvKelembapanValue.text = getString(R.string.humidity_format, kelembapan)
            binding.tvSuhuValue.text = getString(R.string.temperature_format, suhu)
            binding.tvTDSValue.text = getString(R.string.tds_format, tds)
            binding.tvpHValue.text = getString(R.string.ph_format, ph)

            (activity as? DetailHydroponicStatisticActivity)?.adjustFragmentHeight()
        }
    }


    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = StatistikFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
