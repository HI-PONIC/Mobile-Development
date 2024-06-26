package com.example.hi_ponic.view.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hi_ponic.data.Response.LoginResponse
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.pref.UserModel
import com.example.hi_ponic.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginUser = MutableLiveData<LoginResponse>()
    val loginUser: LiveData<LoginResponse> = _loginUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                val user = UserModel(
                    response.loginResult?.name.toString(),
                    email,
                    response.loginResult?.token.toString()
                )
                saveSession(user)
                _isLoading.value = false
                _loginUser.value = response
            } catch (e: Exception) {
                _isLoading.value = false
                _isError.value = e.message ?: "Unknown error occurred"
            }
        }
    }

    fun getCode(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.forgotPassword(email)
                _isLoading.value = false
                _isSuccess.value = true
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isError.value = errorMessage!!
                _isLoading.value = false
                _isSuccess.value = false
            } catch (e: Exception) {
                _isError.value = e.message ?: "Unknown error occurred"
                _isLoading.value = false
                _isSuccess.value = false
            }
        }
    }

    fun resetPassword(code: String, newPassword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.resetPassword(code, newPassword)
                _isLoading.value = false
                _isSuccess.value = true
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isError.value = errorMessage!!
                _isLoading.value = false
                _isSuccess.value = false
            } catch (e: Exception) {
                _isError.value = e.message ?: "Unknown error occurred"
                _isLoading.value = false
                _isSuccess.value = false
            }
        }
    }
}
