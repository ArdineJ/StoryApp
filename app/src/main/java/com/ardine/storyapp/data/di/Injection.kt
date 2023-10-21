package com.ardine.storyapp.data.di

import android.content.Context
import com.ardine.storyapp.data.UserRepository
import com.ardine.storyapp.data.pref.UserPreference
import com.ardine.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}