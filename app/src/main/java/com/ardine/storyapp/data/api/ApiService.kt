package com.ardine.storyapp.data.api

import com.ardine.storyapp.data.response.DetailStoryResponse
import com.ardine.storyapp.data.response.FileUploadResponse
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.data.response.RegisterResponse
import com.ardine.storyapp.data.response.StoryResponse
import com.google.android.gms.maps.model.LatLng
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("location") location: Int = 0,
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImageWithLocation(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("location")  latLng: LatLng,
    ): FileUploadResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1,
    ): StoryResponse
}