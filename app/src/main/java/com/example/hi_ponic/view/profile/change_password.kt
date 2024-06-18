package com.example.hi_ponic.view.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityChangePasswordBinding
import com.example.hi_ponic.view.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class change_password : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAppBar()
        setupListeners()
        observeViewModel()
    }

    private fun setupAppBar() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupListeners() {
        binding.submitButton.setOnClickListener {
            val currentPassword = binding.passwordEditText.text.toString()
            val newPassword = binding.confirmpasswordEditText.text.toString()
            showConfirmationDialog(currentPassword, newPassword)
        }
    }

    private fun showConfirmationDialog(currentPassword: String, newPassword: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Confirm Password Change")
            setMessage("Are you sure you want to change your password?")
            setPositiveButton("Yes") { dialog, _ ->

                profileViewModel.changePassword(currentPassword, newPassword)
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            profileViewModel.changePasswordResponse.collect { response ->
                response?.let {
                    Toast.makeText(this@change_password, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
