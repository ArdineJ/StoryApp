package com.ardine.storyapp.view.maps

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository

class MapsViewModel(private val repository: Repository) : ViewModel(){
    fun getStoriesWithLocation(token: String) = repository.getStoriesWithLocation(token)
}