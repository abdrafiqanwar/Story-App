package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.pref.UserRepository
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class RegisterViewModel(private val repository: UserRepository) : ViewModel(){
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}