package com.ardine.storyapp.data.di

import android.content.Context
import com.ardine.storyapp.data.Repository
import com.ardine.storyapp.data.api.ApiConfig
import com.ardine.storyapp.data.database.StoryDatabase
import com.ardine.storyapp.data.pref.UserPreference
import com.ardine.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        val database = StoryDatabase.getDatabase(context)
        return Repository.getInstance(apiService,pref,database)
    }
}