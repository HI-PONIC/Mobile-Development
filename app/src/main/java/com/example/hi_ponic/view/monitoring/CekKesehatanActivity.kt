package com.example.hi_ponic.view.monitoring

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hi_ponic.BuildConfig
import com.example.hi_ponic.R
import com.example.hi_ponic.databinding.ActivityCekKesehatanBinding
import java.io.File

class CekKesehatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCekKesehatanBinding
    private lateinit var currentPhotoPath: String
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cek_kesehatan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityCekKesehatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openGalleryOrCamera()
        topAppbarHandle()
    }

    private fun topAppbarHandle() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun openGalleryOrCamera() {
        binding.ivDetail.setOnClickListener{
            pickImage()
        }
    }

    private fun pickImage() {
        val options = arrayOf<CharSequence>("Take photo", "Choose from gallery", "Cancel")
        val builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
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
                            "${BuildConfig.APPLICATION_ID}.fileprovider", // Ensure the authority is correct
                            photoFile
                        )
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePicture, 0) // 0 for request code
                    }
                }
                options[item] == "Choose from gallery" -> {
                    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1) // 1 for request code
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
                    Log.d("CekKesehatanActivity", "Photo capture intent result received")
                    val file = File(currentPhotoPath)
                    if (file.exists()) {
                        Log.d("CekKesehatanActivity", "Photo file exists at: $currentPhotoPath")
                        val reducedFile = file.reduceFileImage()
                        val bitmap = BitmapFactory.decodeFile(reducedFile.path)
                        if (bitmap != null) {
                            binding.ivDetail.setImageBitmap(bitmap)
                        } else {
                            Log.e("CekKesehatanActivity", "Error: Bitmap decode failed")
                            Toast.makeText(this, "Error: Bitmap decode failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("CekKesehatanActivity", "Error: File not found at $currentPhotoPath")
                        Toast.makeText(this, "Error: File not found", Toast.LENGTH_SHORT).show()
                    }
                }
                1 -> {
                    Log.d("CekKesehatanActivity", "Gallery intent result received")
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        Log.d("CekKesehatanActivity", "Image selected from gallery: $it")
                        val file = uriToFile(it, applicationContext)
                        val reducedFile = file.reduceFileImage()
                        val bitmap = BitmapFactory.decodeFile(reducedFile.path)
                        if (bitmap != null) {
                            binding.ivDetail.setImageBitmap(bitmap)
                        } else {
                            Log.e("CekKesehatanActivity", "Error: Bitmap decode failed")
                            Toast.makeText(this, "Error: Bitmap decode failed", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Log.e("CekKesehatanActivity", "Error: Selected image URI is null")
                        Toast.makeText(this, "Error: Selected image URI is null", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Log.e("CekKesehatanActivity", "Error: Result not OK, code: $resultCode")
        }
    }
}
