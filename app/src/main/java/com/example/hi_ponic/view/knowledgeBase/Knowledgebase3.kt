package com.example.hi_ponic.view.knowledgeBase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityKnowledgebase3Binding
import com.example.hi_ponic.databinding.ActivityKnowledgebase4Binding

class Knowledgebase3 : AppCompatActivity() {
    private lateinit var binding: ActivityKnowledgebase3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_knowledgebase3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityKnowledgebase3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        topAppbarHandle()
    }

    private fun topAppbarHandle() {
        binding.topAppbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}