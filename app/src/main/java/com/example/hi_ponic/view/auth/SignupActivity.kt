package com.example.hi_ponic.view.auth

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivitySignupBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private val viewmodel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.md_theme_secondaryContainer)

        setupInsets()
        keyboardAdjuster()
        onRegister()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun onRegister() {
        binding.tvSignup.setOnClickListener {
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            showLoading(true)

            viewmodel.register(name, email, password)

            viewmodel.isLoading.observe(this) { showLoading(it) }

            viewmodel.registerUser.observe(this) {
                Snackbar.make(binding.main, "Registration successful", Snackbar.LENGTH_LONG).show()
            }
            viewmodel.isError.observe(this) {
                Snackbar.make(binding.main,"Registration Unsuccesful", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun keyboardAdjuster() {
        binding.scrollView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.scrollView.getWindowVisibleDisplayFrame(r)
            val screenHeight: Int = binding.scrollView.rootView.height
            val keypadHeight: Int = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) { // if keyboard is visible
                binding.scrollView.post {
                    binding.scrollView.smoothScrollTo(0, binding.scrollView.bottom)
                }
            }
        }
    }
}
