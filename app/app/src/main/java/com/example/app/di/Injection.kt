package com.example.app.di

import android.content.Context
import com.example.app.data.retrofit.ApiConfig
import com.example.app.pref.UserPreference
import com.example.app.pref.UserRepository
import com.example.app.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }
}