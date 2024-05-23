package com.example.hi_ponic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //UJI COBA

        binding.btnlogin.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnsignup.setOnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}