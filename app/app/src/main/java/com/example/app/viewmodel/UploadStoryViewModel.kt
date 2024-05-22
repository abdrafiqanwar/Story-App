package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.pref.StoryRepository
import com.example.app.pref.UserRepository
import java.io.File

class UploadStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun uploadStory(description: String, photo: File?) = repository.uploadStory(description, photo)
}