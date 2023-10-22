package com.ardine.storyapp.view.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardine.storyapp.data.UserRepository
import com.ardine.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int>
        get() = _loadingVisibility

    fun showLoading() {
        _loadingVisibility.value = View.VISIBLE
    }

    fun hideLoading() {
        _loadingVisibility.value = View.GONE
    }

    fun login(email: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                onLoginResult(true)
            } catch (e: Exception) {
                onLoginResult(false)
            } finally {
                hideLoading()
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}