package com.example.hi_ponic.view.profile

import android.content.Intent
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
import com.example.hi_ponic.databinding.ActivityEditUsernameBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.mainView.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                showConfirmationDialog(newUsername)
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.gantipasword.setOnClickListener {
            val intent = Intent(this, change_password::class.java)
            startActivity(intent)
        }

        observeViewModel()
    }

    private fun showConfirmationDialog(newUsername: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Confirm Changes")
            setMessage("Are you sure you want to change your username to \"$newUsername\"?")
            setPositiveButton("Yes") { dialog, _ ->
                profileViewModel.changeUsername(newUsername)
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
            profileViewModel.changeUsernameResponse.collect { response ->
                response?.let {
                    Toast.makeText(this@edit_username, it.message, Toast.LENGTH_SHORT).show()
                    if (response.error == false) {
                        val intent = Intent(this@edit_username, MainActivity::class.java)
                        startActivity(intent)
                        finish()  // Finish the activity when the username change is successful
                    }
                }
            }
        }
    }
}
