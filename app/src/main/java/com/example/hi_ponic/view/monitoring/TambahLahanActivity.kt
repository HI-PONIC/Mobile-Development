package com.example.hi_ponic.view.monitoring

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.BuildConfig
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityTambahLahanBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.mainView.MainActivity
import com.example.hi_ponic.view.monitoring.view_model.TambahLahanViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TambahLahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahLahanBinding
    private lateinit var currentPhotoPath: String
    private lateinit var photoURI: Uri
    private lateinit var selectedFile: File

    private val viewModel by viewModels<TambahLahanViewModel> {
        ViewModelFactory.getInstance(this)
    }

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
        binding.ivDetail.setOnClickListener { pickImage() }
        handleSubmitButton()
    }

    private fun dateInput() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.time
                val formattedDate = formatDateToISOString(selectedDate)
                binding.TanggalEditText.setText(formattedDate)
            }, year, month, day)

        binding.edittextTanggalLayout.setEndIconOnClickListener { datePickerDialog.show() }
        topAppbarHandle()
    }

    private fun formatDateToISOString(date: Date): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return isoFormat.format(date)
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun pickImage() {
        val options = arrayOf("Take photo", "Choose from gallery", "Cancel")
        AlertDialog.Builder(this).apply {
            setTitle("Pick Image")
            setItems(options) { dialog, item ->
                when (options[item]) {
                    "Take photo" -> {
                        if (ContextCompat.checkSelfPermission(
                                this@TambahLahanActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@TambahLahanActivity,
                                arrayOf(Manifest.permission.CAMERA),
                                100
                            )
                        } else {
                            takePhoto()
                        }
                    }

                    "Choose from gallery" -> {
                        val pickFile = Intent(Intent.ACTION_GET_CONTENT).apply {
                            type = "*/*"
                            addCategory(Intent.CATEGORY_OPENABLE)
                        }
                        startActivityForResult(Intent.createChooser(pickFile, "Choose a file"), 1)
                    }

                    "Cancel" -> dialog.dismiss()
                }
            }
            show()
        }
    }

    private fun takePhoto() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File = createCustomTempFile(applicationContext)
        currentPhotoPath = photoFile.absolutePath
        photoURI = FileProvider.getUriForFile(
            this,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            photoFile
        )
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePicture, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0 -> handleCameraImage()
                1 -> handleGalleryImage(data)
            }
        }
    }

    private fun handleCameraImage() {
        val file = File(currentPhotoPath)
        if (file.exists()) {
            selectedFile = file.reduceFileImage()
            val bitmap = BitmapFactory.decodeFile(selectedFile.path)
            binding.ivDetail.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Error: File not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleGalleryImage(data: Intent?) {
        val selectedImageUri: Uri? = data?.data
        selectedImageUri?.let {
            val file = uriToFile(it, applicationContext)
            selectedFile = file.reduceFileImage()
            val bitmap = BitmapFactory.decodeFile(selectedFile.path)
            binding.ivDetail.setImageBitmap(bitmap)
        }
    }

    private fun handleSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            if (::selectedFile.isInitialized) {
                val namaTumbuhan = binding.NamaTumbuhanEditText.text.toString()
                val tanggal = binding.TanggalEditText.text.toString()

                if (namaTumbuhan.isEmpty() || tanggal.isEmpty()) {
                    Toast.makeText(this, "Input all data needed", Toast.LENGTH_SHORT).show()
                } else {
                    showConfirmationDialog(namaTumbuhan, tanggal)
                }
            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog(namaTumbuhan: String, tanggal: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure want to add this plant?")
            setPositiveButton("yes") { _, _ ->
                viewModel.addPlant(namaTumbuhan, tanggal, createMultipartBody(selectedFile))
            }
            setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }

        // Observe the error state outside of the dialog to ensure it always gets observed
        viewModel.error.observe(this@TambahLahanActivity) { hasError ->
            if (hasError) {
                viewModel.isError.observe(this@TambahLahanActivity) { errorMessage ->
                    AlertDialog.Builder(this@TambahLahanActivity).apply {
                        setTitle("Error")
                        setMessage(errorMessage)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            } else {
                Toast.makeText(
                    this@TambahLahanActivity,
                    "Plant added successfully",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@TambahLahanActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun createMultipartBody(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }
}
