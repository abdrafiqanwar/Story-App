package com.example.app.di

import android.content.Context
import com.example.app.pref.UserPreference
import com.example.app.pref.UserRepository
import com.example.app.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}