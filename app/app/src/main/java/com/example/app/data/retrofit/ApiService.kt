package com.example.app.data.retrofit

import com.example.app.data.response.LoginResponse
import com.example.app.data.response.RegisterResponse
import com.example.app.data.response.StoryResponse
import com.example.app.data.response.UploadStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadStoryResponse
}