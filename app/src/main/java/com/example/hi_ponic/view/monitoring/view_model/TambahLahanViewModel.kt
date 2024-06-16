package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class TambahLahanViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun addPlant(name: String, date_added: String, image: MultipartBody.Part) {
        _isLoading.value = true
        _error.value = false
        viewModelScope.launch {
            try {
                val nameRequestBody = name.toRequestBody(MultipartBody.FORM)
                val dateAddedRequestBody = date_added.toRequestBody(MultipartBody.FORM)
                val token = repository.getSession().first().token
                repository.addPlant("Bearer $token", nameRequestBody, dateAddedRequestBody, image)
                _isLoading.value = false
                _error.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isLoading.value = false
                _error.value = true
                _isError.value = errorMessage ?: "Unknown Error"
            }
        }
    }
}