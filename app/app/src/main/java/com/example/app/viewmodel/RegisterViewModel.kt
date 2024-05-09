package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.pref.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel(){
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(name, email, password)
        }
    }
}