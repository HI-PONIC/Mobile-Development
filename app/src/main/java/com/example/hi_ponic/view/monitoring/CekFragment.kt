package com.example.hi_ponic.view.monitoring

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hi_ponic.databinding.FragmentCekBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.monitoring.ml.PrediksiPanenHelper
import com.example.hi_ponic.view.monitoring.view_model.CekPanenViewModel
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class CekFragment : Fragment() {
    private lateinit var binding: FragmentCekBinding

    private val viewModel by viewModels<CekPanenViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var prediksiPanenHelper: PrediksiPanenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prediksiPanenHelper = PrediksiPanenHelper(
            context = requireContext(),
            onResult = { result ->
                val currentDate = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
                binding.tvCekTerakhirHasilPanen.text = "Cek terakhir: $currentDate"

                val roundedResult = result.toDouble().roundToInt()
                val prediksiPanen = 45 - roundedResult

                binding.tvHasilPrediksiPanen.text = "Estimasi panen: $prediksiPanen hari lagi"
            },
            onError = { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        )

        binding.btnCekPanen.setOnClickListener {
            // Manual input
            val temp = 12.0
            val tds = 30.0
            val ph = 4.0
            val humidity = 20.0
            val inputArray = arrayOf(floatArrayOf(temp.toFloat(), tds.toFloat(), ph.toFloat(), humidity.toFloat()))
            val input3DArray = arrayOf(inputArray)
//            Log.d("predict", "Manual input3DArray: ${Arrays.deepToString(input3DArray)}")
            prediksiPanenHelper.predict(input3DArray)
        }
    }
}
