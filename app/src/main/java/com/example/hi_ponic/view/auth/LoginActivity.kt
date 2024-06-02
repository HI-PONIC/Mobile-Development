package com.example.hi_ponic.view.auth

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.data.pref.UserModel
import com.example.hi_ponic.databinding.ActivityLoginBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.mainView.MainActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_primary));
        }

        keyboardAdjuster()

        button()
    }

    private fun button() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailEditText.toString()
            viewModel.saveSession(UserModel(email, "sample_token"))
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
            startActivity(intent)
        }
        binding.buttonSignup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun keyboardAdjuster() {
        binding.scrollView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                binding.scrollView.getWindowVisibleDisplayFrame(r)
                val screenHeight: Int = binding.scrollView.rootView.height
                val keypadHeight: Int = screenHeight - r.bottom
                if (keypadHeight > screenHeight * 0.15) { // jika keyboard terlihat
                    binding.scrollView.post {
                        binding.scrollView.smoothScrollTo(0, binding.scrollView.bottom)
                    }
                }
            }
        })
    }
}