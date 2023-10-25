package com.ardine.storyapp.view.detail

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository

class DetailViewModel (private val repository: Repository) : ViewModel(){
    fun getDetailStory(token: String,id: String) = repository.getDetailStory(token,id)

}