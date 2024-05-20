package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.pref.UserRepository

class MapsViewModel(private val repository: UserRepository) : ViewModel() {
    fun getStoriesWithLocation() = repository.getStoriesWithLocation()
}