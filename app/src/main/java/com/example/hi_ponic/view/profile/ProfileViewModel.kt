package com.example.hi_ponic.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.response.ErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _changeUsernameResponse = MutableStateFlow<ErrorResponse?>(null)
    val changeUsernameResponse: StateFlow<ErrorResponse?> = _changeUsernameResponse

    private val _changePasswordResponse = MutableStateFlow<ErrorResponse?>(null)
    val changePasswordResponse: StateFlow<ErrorResponse?> = _changePasswordResponse

    fun changeUsername(newUsername: String) {
        viewModelScope.launch {
            val response = repository.changeUsername(newUsername)
            _changeUsernameResponse.value = response
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            val response = repository.changePassword(newPassword, currentPassword)
            _changePasswordResponse.value = response
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
