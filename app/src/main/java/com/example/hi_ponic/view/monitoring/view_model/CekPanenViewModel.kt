package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.Response.SensorData
import com.example.hi_ponic.data.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CekPanenViewModel(private val repository: UserRepository) : ViewModel() {

    private val _cekPanen = MutableLiveData<SensorData>()
    val cekPanen: LiveData<SensorData> = _cekPanen

    init {
        getSensorValue()
    }

    private fun getSensorValue() {
        viewModelScope.launch {
            try {
                val response = repository.getSensorValue()
                _cekPanen.value = response.sensorData!!
            } catch (e: Exception) {
                Log.d("error", "sensor error", e)
            }
        }
    }
}
