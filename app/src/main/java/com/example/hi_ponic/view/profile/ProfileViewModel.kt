package com.example.hi_ponic.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.response.ErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _changeUsernameResponse = MutableStateFlow<ErrorResponse?>(null)
    val changeUsernameResponse: StateFlow<ErrorResponse?> = _changeUsernameResponse

    private val _changePasswordResponse = MutableStateFlow<ErrorResponse?>(null)
    val changePasswordResponse: StateFlow<ErrorResponse?> = _changePasswordResponse

    fun changeUsername(newUsername: String) {
        viewModelScope.launch {
            repository.getSession().collect { userModel ->
                val token = "Bearer ${userModel.token}"
                val response = repository.changeUsername(token, newUsername)
                _changeUsernameResponse.value = response
                if (response.error == false && !response.message.isNullOrEmpty()) {
                    userPreference.saveUsername(newUsername)
                }
            }
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            repository.getSession().collect { userModel ->
                val token = "Bearer ${userModel.token}"
                val response = repository.changePassword(token, currentPassword, newPassword)
                _changePasswordResponse.value = response
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val logoutResponse = repository.logout()
            // Handle logout response if needed
        }
    }
}
