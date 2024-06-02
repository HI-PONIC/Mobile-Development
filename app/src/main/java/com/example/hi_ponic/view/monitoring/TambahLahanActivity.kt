package com.example.hi_ponic.view.monitoring

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityTambahLahanBinding
import java.util.Calendar

class TambahLahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahLahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_lahan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityTambahLahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateInput()
    }

    private fun dateInput() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedyear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/$selectedMonth/$selectedyear"
            binding.TanggalEditText.setText(selectedDate)
        }, year, month, day)



        binding.edittextTanggalLayout.setEndIconOnClickListener {
            datePickerDialog.show()
        }
    }
}