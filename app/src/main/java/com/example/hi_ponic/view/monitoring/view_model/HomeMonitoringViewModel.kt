package com.example.hi_ponic.view.monitoring.view_model

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ListTanamanResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeMonitoringViewModel(private val repository: UserRepository) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _plantData = MutableLiveData<ListTanamanResponse>()
    val plantData: LiveData<ListTanamanResponse> = _plantData

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

}