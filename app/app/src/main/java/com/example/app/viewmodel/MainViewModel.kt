package com.example.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.app.data.response.ListStoryItem
import com.example.app.pref.StoryRepository
import com.example.app.pref.UserModel
import com.example.app.pref.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    val getAllStories: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)
}