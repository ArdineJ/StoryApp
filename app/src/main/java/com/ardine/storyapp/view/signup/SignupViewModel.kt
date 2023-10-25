package com.ardine.storyapp.view.signup

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository

class SignupViewModel (private val repository: Repository) : ViewModel()  {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}