package com.example.hi_ponic.view.knowledgeBase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityCekKesehatanBinding
import com.example.hi_ponic.databinding.ActivityDetailKnowledgeBaseBinding

class DetailKnowledgeBaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailKnowledgeBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding
        setContentView(R.layout.activity_detail_knowledge_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityDetailKnowledgeBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topAppbarHandle()
    }
    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}