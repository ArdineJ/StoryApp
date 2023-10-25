package com.ardine.storyapp.view.camera

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.UserRepository
import java.io.File

class MediaViewModel(private val repository: UserRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}