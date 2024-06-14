package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class TambahLahanViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addPlant(name: String, date_added: String, image: MultipartBody.Part) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val nameRequestBody = name.toRequestBody(MultipartBody.FORM)
                val dateAddedRequestBody = date_added.toRequestBody(MultipartBody.FORM)
                val token = repository.getSession().first().token
                repository.addPlant("Bearer $token", nameRequestBody, dateAddedRequestBody, image)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("TambahLahanViewModel", "Error adding plant", e)
                _isLoading.value = false
            }
        }
    }
}
