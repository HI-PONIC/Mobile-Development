package com.example.hi_ponic.view.monitoring

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.hi_ponic.R
import com.example.hi_ponic.data.pref.PanenPreference
import com.example.hi_ponic.data.pref.panenDataStore
import com.example.hi_ponic.databinding.FragmentCekBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.monitoring.ml.PrediksiPanenHelper
import com.example.hi_ponic.view.monitoring.view_model.CekPanenViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class CekFragment : Fragment() {
    private lateinit var binding: FragmentCekBinding

    private val viewModel by viewModels<CekPanenViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var prediksiPanenHelper: PrediksiPanenHelper
    private lateinit var panenPreference: PanenPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        panenPreference = PanenPreference.getInstance(requireContext().panenDataStore)
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

        // Register broadcast receiver
        val filter = IntentFilter("com.example.hi_ponic.RESULT_ACTION")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(resultReceiver, filter)

        // Load saved data
        val id = arguments?.getInt(CekFragment.ARG_ID) ?: 0
        loadSavedData(id)

        prediksiPanenHelper = PrediksiPanenHelper(
            context = requireContext(),
            onResult = { result ->
                val currentDate = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
                binding.tvCekTerakhirHasilPanen.text = currentDate

                val roundedResult = result.toDouble().roundToInt()
                val prediksiPanen = 45 - roundedResult

                binding.tvHasilPrediksiPanen.text =
                    getString(R.string.estimasi_panen_hari_lagi, prediksiPanen)

                // Save to DataStore
                lifecycleScope.launch {
                    panenPreference.savePanenData(id, currentDate, prediksiPanen)
                }

                (activity as? DetailHydroponicStatisticActivity)?.adjustFragmentHeight()
            },
            onError = { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        )

        binding.btnCekPanen.setOnClickListener {
            viewModel.getSensorValue(id)
            viewModel.cekPanen.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    val temp = data.avgTemp
                    val tds = data.avgTds
                    val ph = data.avgPh
                    val humidity = data.avgHumidity

                    if (temp != null && tds != null && ph != null && humidity != null) {
                        val inputArray = arrayOf(floatArrayOf(temp, tds, ph, humidity))
                        val input3DArray = arrayOf(inputArray)
                        prediksiPanenHelper.predict(input3DArray)
                    } else {
                        Toast.makeText(requireContext(), "Sensor data is incomplete", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve sensor data", Toast.LENGTH_SHORT).show()
                }

                (activity as? DetailHydroponicStatisticActivity)?.adjustFragmentHeight()
            }
        }

        binding.btnCekKesehatanTanaman.setOnClickListener {
            val intent = Intent(requireContext(), CekKesehatanActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loadSavedData(id: Int) {
        lifecycleScope.launch {
            val sharedPreferences = requireContext().getSharedPreferences("CekKesehatanPrefs", Context.MODE_PRIVATE)
            val lastResult = sharedPreferences.getString("lastResult", "No result")
            val lastImagePath = sharedPreferences.getString("lastImagePath", null)

            // Ambil data terakhir cek dari panenPreference
            val panenData = panenPreference.getPanenData(id).first()
            val lastCheckDate = if (panenData.lastCheckDate.isNotEmpty()) panenData.lastCheckDate else "No date available"

            // Tampilkan tanggal terakhir cek
            binding.cekterakhir.text = "Last Check: $lastCheckDate"

            binding.HasilKlasisifikasi.text = "Result: $lastResult"

            if (lastImagePath != null) {
                val file = File(lastImagePath)
                if (file.exists()) {
                    val bitmap = BitmapFactory.decodeFile(file.path)
                    binding.gambarupdate.setImageBitmap(bitmap)
                }
            }

            if (panenData.lastCheckDate.isNotEmpty() && panenData.predictionResult != -1) {
                binding.tvCekTerakhirHasilPanen.text = "${panenData.lastCheckDate}"
                binding.tvHasilPrediksiPanen.text = "Harvest estimate: ${panenData.predictionResult} days left"
            }
        }
    }


    private val resultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = arguments?.getInt(CekFragment.ARG_ID) ?: 0
            loadSavedData(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(resultReceiver)
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = CekFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
