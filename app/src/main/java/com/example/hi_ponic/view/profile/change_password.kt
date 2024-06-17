package com.example.hi_ponic.view.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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

        topAppbarHandle()
        setupListeners()
        observeViewModel()
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupListeners() {
        binding.submitButton.setOnClickListener {
            val currentPassword = binding.passwordEditText.text.toString()
            val newPassword = binding.confirmpasswordEditText.text.toString()
            profileViewModel.changePassword(currentPassword, newPassword)
        }
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
