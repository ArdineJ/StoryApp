package com.ardine.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ardine.storyapp.data.api.ApiService
import com.ardine.storyapp.data.pref.UserModel
import com.ardine.storyapp.data.pref.UserPreference
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.data.response.RegisterResponse
import com.ardine.storyapp.data.response.StoryResponse
import kotlinx.coroutines.flow.Flow

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

    fun getStories(token: String): LiveData<ResultState<StoryResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.getStories("Bearer $token")
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
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}
