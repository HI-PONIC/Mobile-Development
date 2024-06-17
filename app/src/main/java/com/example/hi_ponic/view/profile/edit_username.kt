package com.example.hi_ponic.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityEditUsernameBinding
import com.example.hi_ponic.view.ViewModelFactory

class edit_username : AppCompatActivity() {

    private lateinit var binding: ActivityEditUsernameBinding
    private val profileViewModel: ProfileViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editProfileButton.setOnClickListener {
            val newUsername = binding.usernameEditText.text.toString().trim()
            if (newUsername.isNotEmpty()) {
                profileViewModel.changeUsername(newUsername)
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }

        // Ensure correct import for Observe

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.gantipasword.setOnClickListener {
            val intent = Intent(this, change_password::class.java)
            startActivity(intent)
        }
    }
}
