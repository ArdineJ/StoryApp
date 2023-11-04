package com.ardine.storyapp.view.camera

import androidx.lifecycle.ViewModel
import com.ardine.storyapp.data.Repository
import com.google.android.gms.maps.model.LatLng
import java.io.File

class MediaViewModel(private val repository: Repository) : ViewModel() {
    fun uploadImage(token: String, file: File, description: String) = repository.uploadImage(token, file, description)

    fun uploadImageWithLocation(token: String, file: File, description: String, latLng: LatLng) = repository.uploadImageWithLocation(token, file, description, latLng)
}