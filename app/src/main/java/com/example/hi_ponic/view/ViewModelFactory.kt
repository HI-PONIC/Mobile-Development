package com.example.hi_ponic.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hi_ponic.data.UserRepository
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.pref.dataStore
import com.example.hi_ponic.di.Injection
import com.example.hi_ponic.view.auth.LoginViewModel
import com.example.hi_ponic.view.auth.SignupViewModel
import com.example.hi_ponic.view.mainView.MainViewModel
import com.example.hi_ponic.view.monitoring.view_model.CekKesehatanViewModel
import com.example.hi_ponic.view.monitoring.view_model.CekPanenViewModel
import com.example.hi_ponic.view.monitoring.view_model.HomeMonitoringViewModel
import com.example.hi_ponic.view.monitoring.view_model.StatistikViewModel
import com.example.hi_ponic.view.monitoring.view_model.TambahLahanViewModel
import com.example.hi_ponic.view.profile.ProfileViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository, userPreference) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CekPanenViewModel::class.java) -> {
                CekPanenViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CekKesehatanViewModel::class.java) -> {
                CekKesehatanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StatistikViewModel::class.java) -> {
                StatistikViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeMonitoringViewModel::class.java) -> {
                HomeMonitoringViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TambahLahanViewModel::class.java) -> {
                TambahLahanViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            val repository = Injection.provideRepository(context)
            val userPreference = UserPreference.getInstance(context.dataStore)
            return ViewModelFactory(repository, userPreference)
        }
    }
}
