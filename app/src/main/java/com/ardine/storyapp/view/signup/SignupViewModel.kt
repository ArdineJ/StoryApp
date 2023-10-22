package com.ardine.storyapp.view.signup

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int>
        get() = _loadingVisibility

    fun showLoading() {
        _loadingVisibility.value = View.VISIBLE
    }

    fun hideLoading() {
        _loadingVisibility.value = View.GONE
    }
}
