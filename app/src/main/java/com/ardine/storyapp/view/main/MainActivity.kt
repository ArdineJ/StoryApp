package com.ardine.storyapp.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.databinding.ActivityMainBinding
import com.ardine.storyapp.view.ViewModelFactory
import com.ardine.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                setupView(user.token)
                setupAction()
            }
        }

    }

    private fun setupView(token: String) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
        viewModel.getStories(token).observe(this){result ->
            if (result != null){
                when (result) {
                    ResultState.Loading -> {
                        binding.loadingProgressBar.isVisible = true
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.isVisible = false
                    }

                    is ResultState.Success -> {
                        binding.loadingProgressBar.isVisible = false
                        result.data.listStory
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

}