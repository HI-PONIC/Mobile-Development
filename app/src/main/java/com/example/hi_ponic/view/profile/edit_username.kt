package com.example.hi_ponic.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityChangePasswordBinding
import com.example.hi_ponic.databinding.ActivityEditUsernameBinding

class edit_username : AppCompatActivity() {
    private lateinit var binding: ActivityEditUsernameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_username)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityEditUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menambahkan onClickListener untuk tombol ganti password
        findViewById<Button>(R.id.gantipasword).setOnClickListener {
            // Navigasi ke activity Ganti Password
            val intent = Intent(this, change_password::class.java)
            startActivity(intent)
        }
        topAppbarHandle()
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}