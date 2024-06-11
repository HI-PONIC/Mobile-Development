package com.example.hi_ponic.data

import com.example.hi_ponic.data.Response.SensorResponse
import com.example.hi_ponic.data.pref.UserModel
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference, private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

//    suspend fun login(identifier: String,password: String ):LoginResponse{
//        return apiService.login(identifier,password)
//    }

    suspend fun register(password: String,email: String, username: String): ErrorResponse{
        return apiService.register(username,email,password)
    }

    suspend fun getSensorValue():SensorResponse{
        return apiService.getSensorData()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) =  UserRepository(userPreference,apiService)
    }
}