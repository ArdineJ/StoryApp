package com.ardine.storyapp.view.detail

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.UserRepository

class DetailViewModel (private val repository: UserRepository) : ViewModel(){
    fun getDetailStory(token: String,id: String) = repository.getDetailStory(token,id)

}