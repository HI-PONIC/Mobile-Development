package com.example.hi_ponic.view.monitoring.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.Response.SensorData
import com.example.hi_ponic.data.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StatistikViewModel(private val repository: UserRepository) : ViewModel() {

    private val _dataStatistik = MutableLiveData<SensorData>()
    val dataStatistik: LiveData<SensorData> = _dataStatistik

    init {
        observeRTSensorValue()
    }

    private fun observeRTSensorValue() {
        viewModelScope.launch {
            try {
                repository.observeSensorValues().collect { sensorData ->
                    _dataStatistik.postValue(sensorData.sensorData!!)
                }
                delay(120000)
            } catch (e: Exception) {
                Log.d("error", "sensor error", e)
            }
        }
    }
}
