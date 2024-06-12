package com.example.hi_ponic.view.monitoring.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository import com.example.hi_ponic.data.Response.PredictConditionResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import android.util.Log

class CekKesehatanViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _predictResult = MutableLiveData<PredictConditionResponse>()
    val predictResult: LiveData<PredictConditionResponse> = _predictResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Ensure loading is set to true when upload starts
                val response = userRepository.uploadImage(file)
                if (response.status == "success") {
                    _predictResult.value = response
                    Log.d("CekKesehatanViewModel", "Upload success: ${response.data?.result}")
                } else {
                    _predictResult.value = PredictConditionResponse(
                        status = "error"
                    )
                    Log.e("CekKesehatanViewModel", "Unexpected response: ${response.data}")
                }
            } catch (e: Exception) {
                _predictResult.value = PredictConditionResponse(
                    status = "error"
                )
                Log.e("CekKesehatanViewModel", "Upload error: ${e.message}")
            } finally {
                _isLoading.value = false // Ensure loading is set to false when upload ends
            }
        }
    }
}


