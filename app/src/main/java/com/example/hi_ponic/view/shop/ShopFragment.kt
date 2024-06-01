package com.example.hi_ponic.view.shop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hi_ponic.databinding.FragmentShopBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShopFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting click listeners for each button
        binding.buttonPesanAlatIot.setOnClickListener {
            openLink("https://www.tokopedia.com/cncstorebandung/esp32-esp-32-doit-wifi-bluetooth-iot-esp-32s-development-board-38-pin?extParam=ivf%3Dfalse&src=topads")
        }

        binding.buttonPesanHidroponic.setOnClickListener {
            openLink("https://www.tokopedia.com/happygardenindonesia/paket-hidroponik-wick-9-lubang-3-bak-lengkap-paket-super-hemat?extParam=ivf%3Dfalse&src=topads")
        }

        binding.buttonPesanBibitCabai.setOnClickListener {
            openLink("https://www.tokopedia.com/benihbibitseribu/benih-seribuan-bibit-cabe-carolina-reaper-merah-cabai-terpedas-premium-prem-carolina-d8fb?extParam=ivf%3Dfalse%26src%3Dsearch")
        }

        binding.buttonPesanBibitSawi.setOnClickListener {
            openLink("https://www.tokopedia.com/benihbibitseribu/bibit-sawi-manis-hongkong-unggul-benih-sayuran-seribuan-prem-swmanis-hk?extParam=ivf%3Dfalse%26src%3Dsearch")
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
