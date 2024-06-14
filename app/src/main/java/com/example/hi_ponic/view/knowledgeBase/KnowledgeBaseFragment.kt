package com.example.hi_ponic.view.knowledgeBase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hi_ponic.databinding.FragmentKnowledgeBaseBinding

class KnowledgeBaseFragment : Fragment() {

    private lateinit var binding: FragmentKnowledgeBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKnowledgeBaseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardView1.setOnClickListener{
            val intent = Intent(requireContext(),Knowledgebase1::class.java)
            startActivity(intent)
        }

        binding.cardView2.setOnClickListener{
            val intent = Intent(requireContext(),Knowledgebase2::class.java)
            startActivity(intent)
        }

        binding.cardView3.setOnClickListener{
            val intent = Intent(requireContext(),Knowledgebase3::class.java)
            startActivity(intent)
        }

        binding.cardView4.setOnClickListener{
            val intent = Intent(requireContext(),Knowledgebase4::class.java)
            startActivity(intent)
        }
    }
}