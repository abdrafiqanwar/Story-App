package com.example.app.pref

import com.example.app.data.response.LoginResponse
import com.example.app.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun register(name: String, email: String, password: String) {
        val apiService = ApiConfig.getApiService()
        val response = apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String) {
        val apiService = ApiConfig.getApiService().login(email, password)
        var token = ""
        apiService.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        token = responseBody.loginResult?.token.toString()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        saveSession(UserModel(email, token))
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}