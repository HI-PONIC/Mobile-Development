package com.example.hi_ponic.view.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hi_ponic.R
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.pref.dataStore
import com.example.hi_ponic.databinding.FragmentProfileBinding
import com.example.hi_ponic.view.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize userPreference here
        userPreference = UserPreference.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), edit_username::class.java)
            startActivity(intent)
        }

        binding.aboutAppButton.setOnClickListener {
            val intent = Intent(requireContext(), tentang_aplikasi::class.java)
            startActivity(intent)
        }

        binding.logoutIcon.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        setData()
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, id ->
                viewModel.logout()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun setData() {
        lifecycleScope.launch {
            val name = userPreference.getName().first()
            val email = userPreference.getSession().first().email
            binding.usernameTextView.text = getString(R.string.name, name)

            binding.emailTextView.text = email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
