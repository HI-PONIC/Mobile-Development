package com.example.hi_ponic

import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.databinding.ActivityDetailHydroponicStatisticBinding
import com.example.hi_ponic.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keyboardAdjuster()
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