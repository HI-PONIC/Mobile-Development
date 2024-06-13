package com.example.hi_ponic.view.monitoring.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class TambahLahanViewModel(private val repository: UserRepository) : ViewModel() {

    fun addPlant(name:String, date_added: String, image: MultipartBody.Part){
        viewModelScope.launch {
            try {

            }catch (e: Exception){

            }
        }
    }
}