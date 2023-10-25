package com.ardine.storyapp.view.login

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}