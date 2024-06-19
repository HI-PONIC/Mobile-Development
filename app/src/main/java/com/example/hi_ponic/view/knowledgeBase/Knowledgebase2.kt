package com.example.hi_ponic.view.knowledgeBase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityKnowledgebase3Binding

class Knowledgebase2 : AppCompatActivity() {

    private lateinit var binding: ActivityKnowledgebase3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityKnowledgebase3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top)
            insets
        }

        topAppbarHandle()
    }

    private fun topAppbarHandle() {
        binding.topAppbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}