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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StatistikViewModel(private val repository: UserRepository) : ViewModel() {

    private val _dataStatistik = MutableLiveData<SensorData>()
    val dataStatistik: LiveData<SensorData> = _dataStatistik

    private var plantId: Int = 0

    fun setId(id: Int) {
        plantId = id
        observeRTSensorValue(plantId)
    }

    private fun observeRTSensorValue(id: Int) {
        viewModelScope.launch {
            try {
                val token = repository.getSession().first().token
                repository.observeSensorValues("Bearer $token", id).collect { sensorData ->
                    _dataStatistik.postValue(sensorData.sensorData!!)
                    Log.d("StatistikViewModel", "Sensor data updated")
                }
            } catch (e: Exception) {
                Log.e("StatistikViewModel", "Failed to observe sensor values", e)
            }
        }
    }
}
