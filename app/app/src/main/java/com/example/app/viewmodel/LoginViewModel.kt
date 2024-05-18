package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.pref.UserModel
import com.example.app.pref.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(token: String) {
        viewModelScope.launch {
            repository.saveSession(UserModel(token))
        }
    }
}