package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.pref.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStoriesWithLocation() = repository.getStoriesWithLocation()
}