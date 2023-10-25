package com.ardine.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardine.storyapp.data.UserRepository
import com.ardine.storyapp.data.di.Injection
import com.ardine.storyapp.view.camera.MediaViewModel
import com.ardine.storyapp.view.detail.DetailViewModel
import com.ardine.storyapp.view.login.LoginViewModel
import com.ardine.storyapp.view.main.MainViewModel
import com.ardine.storyapp.view.signup.SignupViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MediaViewModel::class.java) -> {
                MediaViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}