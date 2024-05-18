package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.pref.UserRepository
import java.io.File

class UploadStoryViewModel(private val repository: UserRepository) : ViewModel() {
    fun uploadStory(description: String, photo: File?) = repository.uploadStory(description, photo)
}