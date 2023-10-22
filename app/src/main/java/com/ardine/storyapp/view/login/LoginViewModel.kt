package com.ardine.storyapp.view.login

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}