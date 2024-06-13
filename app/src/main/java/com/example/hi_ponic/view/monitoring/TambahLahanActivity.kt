package com.example.hi_ponic.view.monitoring

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityTambahLahanBinding
import java.util.*

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

        binding.ivDetail.setOnClickListener {
            pickImage()
        }

        onSubmit()
    }

    private fun onSubmit() {
        binding.btnSubmit.setOnClickListener {
            val namaTumbuhan = binding.NamaTumbuhanEditText.text.toString()
            val tanggal = binding.TanggalEditText.text.toString()

            if (namaTumbuhan.isEmpty() || tanggal.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Konfirmasi")
                    setMessage("Apakah Anda yakin ingin menyimpan data ini?")
                    setPositiveButton("Ya") { _, _ ->
                        // Tambahkan logika penyimpanan data di sini
                        Toast.makeText(this@TambahLahanActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }
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
        topAppbarHandle()
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun pickImage() {
        val options = arrayOf<CharSequence>("Ambil Foto", "Pilih dari Galeri", "Batal")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Sumber Gambar")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Ambil Foto" -> {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
                    } else {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 0) // 0 untuk kode permintaan
                    }
                }
                options[item] == "Pilih dari Galeri" -> {
                    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1) // 1 untuk kode permintaan
                }
                options[item] == "Batal" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0 -> {
                    val selectedImage: Bitmap = data!!.extras!!.get("data") as Bitmap
                    binding.ivDetail.setImageBitmap(selectedImage)
                }
                1 -> {
                    val selectedImage: Uri? = data!!.data
                    binding.ivDetail.setImageURI(selectedImage)
                }
            }
        }
    }
}
