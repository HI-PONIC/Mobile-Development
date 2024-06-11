package com.example.hi_ponic.view.monitoring.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class CekKesehatanViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _dataImage = MutableLiveData<FileUploadResponse>()
//    val dataImage: LiveData<FileUploadResponse> = _dataImage
//
//    fun getCekKesehatan(imagePart: MultipartBody.Part){
//        viewModelScope.launch {
//            try {
//                val token = repository.getSession().first().token
//                if (token.isNotEmpty()) {
//                    val successResponse = repository.uploadStory("Bearer $token", imagePart, description)
//                    _dataImage.value = successResponse
//                } else {
//                    _isError.value = "Token is empty"
//                }
//            } catch (e: HttpException) {
//                val errorBody = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
//                _isError.value = errorResponse.message
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
}