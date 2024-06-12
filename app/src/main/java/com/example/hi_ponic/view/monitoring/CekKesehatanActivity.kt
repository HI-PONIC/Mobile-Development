package com.example.hi_ponic.view.monitoring

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.BuildConfig
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityCekKesehatanBinding
import com.example.hi_ponic.view.ViewModelFactory
import com.example.hi_ponic.view.monitoring.view_model.CekKesehatanViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CekKesehatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCekKesehatanBinding
    private lateinit var currentPhotoPath: String
    private lateinit var photoURI: Uri
    private lateinit var selectedFile: File

    private val viewModel by viewModels<CekKesehatanViewModel> {
        ViewModelFactory.getInstance(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCekKesehatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        openGalleryOrCamera()
        topAppbarHandle()
        handleSubmitButton()


        viewModel.predictResult.observe(this) { response ->
            response?.let {
                val resultMessage = it.data?.result ?: "No result"
                binding.tvHasilKlasifikasi.text = resultMessage
                showSnackbar(resultMessage)
            }
        }
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }



    private fun openGalleryOrCamera() {
        binding.ivDetail.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        val options = arrayOf<CharSequence>("Take photo", "Choose from gallery", "Cancel")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Pick Image")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take photo" -> {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
                    } else {
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
                }
                options[item] == "Choose from gallery" -> {
                    val pickFile = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "*/*"
                        addCategory(Intent.CATEGORY_OPENABLE)
                    }
                    startActivityForResult(Intent.createChooser(pickFile, "Choose a file"), 1)
                }
                options[item] == "Cancel" -> {
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
                    val file = File(currentPhotoPath)
                    if (file.exists()) {
                        val reducedFile = file.reduceFileImage()
                        selectedFile = reducedFile
                        val bitmap = BitmapFactory.decodeFile(reducedFile.path)
                        binding.ivDetail.setImageBitmap(bitmap)
                    } else {
                        Toast.makeText(this, "Error: File not found", Toast.LENGTH_SHORT).show()
                    }
                }
                1 -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        val file = uriToFile(it, applicationContext)
                        val reducedFile = file.reduceFileImage()
                        selectedFile = reducedFile
                        val bitmap = BitmapFactory.decodeFile(reducedFile.path)
                        binding.ivDetail.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun handleSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            if (::selectedFile.isInitialized) {
                uploadImage(selectedFile)
            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImage(file: File) {
        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        viewModel.uploadImage(body)
    }

    private fun showSnackbar(result: String) {
        Snackbar.make(binding.root, result, Snackbar.LENGTH_LONG).show()
    }
}
