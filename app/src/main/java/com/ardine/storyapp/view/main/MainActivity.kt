package com.ardine.storyapp.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.storyapp.R
import com.ardine.storyapp.adapter.StoryListAdapter
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.data.response.ListStoryItem
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
        viewModel.getStory(token).observe(this){result ->
            if (result != null){
                when (result) {
                    ResultState.Loading -> {
                        binding.loadingProgressBar.isVisible = true
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.isVisible = false
                        Toast.makeText(this, result.error ,Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        binding.loadingProgressBar.isVisible = false
                        binding.rvStory.layoutManager = LinearLayoutManager(this)
                        binding.rvStory.adapter = showRecyclerView(result.data.listStory, token)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutButton -> {
                viewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showRecyclerView(list: List<ListStoryItem>, token: String): StoryListAdapter {
        return StoryListAdapter(list, token)
    }

}