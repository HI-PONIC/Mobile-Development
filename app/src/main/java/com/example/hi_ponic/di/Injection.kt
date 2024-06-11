package com.example.hi_ponic.di

import android.content.Context
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.pref.dataStore
import com.example.hi_ponic.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

//object Injection {
//    fun provideRepository(context: Context): UserRepository {
//        val pref = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { pref.getSession().first() }
//        val apiConfig = ApiConfig.getApiService()
//        return UserRepository.getInstance(pref,apiConfig)
//    }
//}