package com.ardine.storyapp.data

import com.ardine.storyapp.data.api.ApiService
import com.ardine.storyapp.data.pref.UserModel
import com.ardine.storyapp.data.pref.UserPreference
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun login(email: String, password: String): LoginResponse = apiService.login(email, password)

    suspend fun register(name: String, email: String, password: String): RegisterResponse = apiService.register(name, email, password)

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
