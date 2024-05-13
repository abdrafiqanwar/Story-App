package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.retrofit.ApiConfig
import com.example.app.data.retrofit.ApiService
import com.example.app.pref.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }
}