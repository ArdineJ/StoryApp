package com.ardine.storyapp.view.camera

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository
import java.io.File

class MediaViewModel(private val repository: Repository) : ViewModel() {
    fun uploadImage(token: String, file: File, description: String) = repository.uploadImage(token, file, description)
}