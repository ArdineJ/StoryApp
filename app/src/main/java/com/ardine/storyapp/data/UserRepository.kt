package com.ardine.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ardine.storyapp.data.api.ApiService
import com.ardine.storyapp.data.pref.UserModel
import com.ardine.storyapp.data.pref.UserPreference
import com.ardine.storyapp.data.response.DetailStoryResponse
import com.ardine.storyapp.data.response.FileUploadResponse
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.data.response.RegisterResponse
import com.ardine.storyapp.data.response.StoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun login(email: String, password: String) : LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.error){
                emit(ResultState.Error(response.message))
            }
            else {
                emit(ResultState.Success(response))
                saveSession(UserModel(email, response.loginResult.token, true))
            }
        } catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name, email, password)
            if (response.error){
                emit(ResultState.Error(response.message))
            }
            else {
                emit(ResultState.Success(response))
            }
        } catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getStory(token: String): LiveData<ResultState<StoryResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.getStory("Bearer $token")
            if (response.error){
                emit(ResultState.Error(response.message))
            }
            else {
                emit(ResultState.Success(response))
            }
        } catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getDetailStory(token: String, id: String): LiveData<ResultState<DetailStoryResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.getDetailStory("Bearer $token", id)
            if(response.error) {
                emit(ResultState.Error(response.message))
            }  else {
                emit(ResultState.Success(response))
            }
        }
        catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
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

    fun uploadImage(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

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
