package com.example.hi_ponic.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel()  {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}