package com.example.hi_ponic.view.monitoring.ml

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.hi_ponic.R
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import com.google.android.gms.tflite.java.TfLite
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegateFactory
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PrediksiPanenHelper(
    private val modelName: String = "model-prediction-panen.tflite",
    val context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit,
) {
    private var isGPUSupported: Boolean = false
    private var interpreter: InterpreterApi? = null

    init {
        initializeTfLite()
    }

    private fun initializeTfLite() {
        Log.d(TAG, "Initializing TensorFlow Lite")
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            Log.d(TAG, "GPU available: $gpuAvailable")
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
                isGPUSupported = true
            }
            TfLite.initialize(context, optionsBuilder.build()).addOnSuccessListener {
                Log.d(TAG, "TensorFlow Lite initialized successfully")
                loadLocalModel()
            }.addOnFailureListener { exception ->
                handleInitializationError("TfLite initialization failed", exception)
            }
        }.addOnFailureListener { exception ->
            handleInitializationError("GPU availability check failed", exception)
        }
    }

    private fun handleInitializationError(message: String, exception: Exception) {
        onError(context.getString(R.string.tflite_is_not_initialized_yet))
        Log.e(TAG, message, exception)
    }

    private fun loadLocalModel() {
        try {
            val buffer: ByteBuffer = loadModelFile(context.assets, modelName)
            initializeInterpreter(buffer)
        } catch (ioException: IOException) {
            handleModelLoadingError(ioException)
        }
    }

    private fun handleModelLoadingError(ioException: IOException) {
        onError(context.getString(R.string.model_loading_error))
        Log.e(TAG, "Model loading failed", ioException)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        assetManager.openFd(modelPath).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }

    private fun initializeInterpreter(model: ByteBuffer) {
        interpreter?.close()
        try {
            val options = InterpreterApi.Options()
                .setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)
            if (isGPUSupported) {
                options.addDelegateFactory(GpuDelegateFactory())
            }
            interpreter = InterpreterApi.create(model, options)
            Log.d(TAG, "TFLite interpreter successfully loaded")
        } catch (e: Exception) {
            handleInterpreterInitializationError(e)
        }
    }

    private fun handleInterpreterInitializationError(e: Exception) {
        onError(context.getString(R.string.interpreter_initialization_failed))
        Log.e(TAG, "Interpreter initialization failed", e)
    }

    fun predict(input: Array<Array<FloatArray>>) {
        if (interpreter == null) {
            onError(context.getString(R.string.no_tflite_interpreter_loaded))
            return
        }

        val outputArray = Array(1) { Array(1) { FloatArray(1) } }

        try {
            val inputShape = interpreter?.getInputTensor(0)?.shape()
            val inputDataType = interpreter?.getInputTensor(0)?.dataType()
            val outputShape = interpreter?.getOutputTensor(0)?.shape()
            val outputDataType = interpreter?.getOutputTensor(0)?.dataType()

            Log.d(TAG, "Input shape: ${inputShape?.contentToString()}")
            Log.d(TAG, "Input data type: $inputDataType")
            Log.d(TAG, "Output shape: ${outputShape?.contentToString()}")
            Log.d(TAG, "Output data type: $outputDataType")

            if (inputShape?.contentEquals(intArrayOf(1, 1, 4)) == true) {
                interpreter?.run(input, outputArray)
                onResult(outputArray[0][0][0].toString())
            } else {
                handleInferenceError("Invalid input shape: ${inputShape?.contentToString()}")
            }
        } catch (e: Exception) {
            handleInferenceError(e.message ?: "Inference error")
        }
    }

    private fun handleInferenceError(message: String) {
        onError(context.getString(R.string.inference_error))
        Log.e(TAG, message)
    }

    fun close() {
        interpreter?.close()
    }

    companion object {
        private const val TAG = "PrediksiPanenHelper"
    }
}
