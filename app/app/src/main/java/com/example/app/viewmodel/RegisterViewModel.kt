package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.pref.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel(){
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}