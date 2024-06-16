package com.example.hi_ponic.view.auth

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityForgotPasswordBinding
import com.example.hi_ponic.databinding.ActivityResetPasswordBinding
import com.example.hi_ponic.view.ViewModelFactory

class ResetPasswordActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resetPassword()
        viewModel.isError.observe(this){
            if (it.isNotEmpty()){
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage(it)
                    setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun resetPassword() {
        binding.buttonResetPassword.setOnClickListener {
            val code = binding.codeEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()

            viewModel.resetPassword(code,newPassword)

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar .visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }
}