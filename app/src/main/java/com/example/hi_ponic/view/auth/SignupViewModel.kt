package com.example.hi_ponic.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ErrorResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerUser = MutableLiveData<ErrorResponse>()
    val registerUser: LiveData<ErrorResponse> = _registerUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.register(name, email, password)
                _isLoading.value = false
                _registerUser.value = response
            } catch (e: Exception) {
                _isLoading.value = false
                _isError.value = e.message ?: "Unknown error occurred"
            }
        }
    }
}