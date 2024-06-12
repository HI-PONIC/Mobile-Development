package com.example.hi_ponic.view.monitoring.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import kotlinx.coroutines.launch

class HomeMonitoringViewModel(private val repository: UserRepository) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun getName(){
        viewModelScope.launch {
            val name = repository.getName()

            _name.value = name.toString()
        }
    }
}