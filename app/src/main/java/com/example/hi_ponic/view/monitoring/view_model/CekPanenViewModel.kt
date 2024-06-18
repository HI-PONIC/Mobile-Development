package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.AverageData
import com.example.hi_ponic.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CekPanenViewModel(private val repository: UserRepository) : ViewModel() {

    private val _cekPanen = MutableLiveData<AverageData?>()
    val cekPanen: LiveData<AverageData?> = _cekPanen

    fun getSensorValue() {
        viewModelScope.launch {
            try {
                val response = repository.getAverageSensor()
                _cekPanen.value = response.averageData
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                Log.d("error", "$errorMessage")
            } catch (e: Exception) {
                Log.d("error", "Unexpected error: ${e.message}")
            }
        }
    }
}
