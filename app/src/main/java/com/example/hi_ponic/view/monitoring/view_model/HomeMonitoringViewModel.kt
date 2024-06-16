package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.data.response.ListTanamanResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeMonitoringViewModel(private val repository: UserRepository) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _plantData = MutableLiveData<ListTanamanResponse>()
    val plantData: LiveData<ListTanamanResponse> = _plantData

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getName(){
        viewModelScope.launch {
            val name = repository.getName()

            _name.value = name.toString()
        }
    }

    fun getPlant(){
        viewModelScope.launch {
            try {
                val token = repository.getSession().first().token
                val response = repository.getPlant("Bearer $token")

                _plantData.value = response
            }catch (e: Exception){
                Log.d("error","list tanamana error")
            }
        }
    }

    fun deletePlant(id: Int){
        viewModelScope.launch {
            try {
                val id = id
                val token = repository.getSession().first().token
                repository.deletePlant("Bearer $token", id)

            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody?.message ?: "Unknown error"
                _isError.value = errorMessage
            }
        }
    }

}