package com.example.hi_ponic.view.auth


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityLoginBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.mainView.MainActivity

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        button()
        setupObservers()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun button() {
//        binding.buttonLogin.setOnClickListener {
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//
//            if (validateInput(email, password)) {
//                loginViewModel.login(email, password)
//                showLoading(true)
//            }
//        }
        binding.buttonSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true
        if (email.isEmpty()) {
            binding.emailEditText.error = getString(R.string.masukkanemail)
            isValid = false
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = getString(R.string.masukkanpassword)
            isValid = false
        }
        return isValid
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupObservers() {
//        loginViewModel.loginUser.observe(this) { response ->
//                showSuccessDialog()
//            }
//        loginViewModel.isLoading.observe(this) { isLoading ->
//            showLoading(isLoading)
//        }
//        loginViewModel.isError.observe(this) { errorMessage ->
//            if (!errorMessage.isNullOrEmpty()) {
//                showError(errorMessage)
//            }
//        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.loginsukses))
            setPositiveButton(getString(R.string.lanjutkelogin)) { _, _ ->
                navigateToMain()
            }
            create()
            show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbarLogin.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}
