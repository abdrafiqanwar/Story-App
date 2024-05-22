package com.example.app.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.app.adapter.PagingSource
import com.example.app.data.response.ErrorResponse
import com.example.app.data.response.ListStoryItem
import com.example.app.data.response.LoginResult
import com.example.app.data.response.RegisterResponse
import com.example.app.data.response.UploadStoryResponse
import com.example.app.data.retrofit.ApiConfig
import com.example.app.data.retrofit.ApiService
import com.example.app.di.Result
import com.example.app.di.reduceFileImage
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.register(name, email, password)

            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.login(email, password)

            if (response.loginResult!= null) {
                emit(Result.Success(response.loginResult))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

//    fun getAllStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
//        emit(Result.Loading)
//
//        try {
//            apiService = ApiConfig.getApiService(getSession().first().token)
//            val response = apiService.getStories()
//            val story = response.listStory
//
//            emit(Result.Success(story))
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.message
//
//            emit(Result.Error(errorMessage.toString()))
//        }
//    }

//    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                PagingSource(apiService)
//            }
//        ).liveData
//    }
//
//    fun uploadStory(description: String, photo: File?): LiveData<Result<UploadStoryResponse>> = liveData {
//        emit(Result.Loading)
//
//        try {
//            val imageFile = reduceFileImage(photo!!)
//
//            val requestBody = description.toRequestBody("text/plain".toMediaType())
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                requestImageFile
//            )
//
//            val response = apiService.uploadStory(requestBody, multipartBody)
//
//            emit(Result.Success(response))
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.message
//
//            emit(Result.Error(errorMessage.toString()))
//        }
//    }
//
//    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
//        emit(Result.Loading)
//
//        try {
//            apiService = ApiConfig.getApiService(getSession().first().token)
//            val response = apiService.getStoriesWithLocation()
//            val story = response.listStory
//
//            emit(Result.Success(story))
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.message
//
//            emit(Result.Error(errorMessage.toString()))
//        }
//    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}