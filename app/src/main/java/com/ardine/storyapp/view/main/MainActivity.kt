package com.ardine.storyapp.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.storyapp.R
import com.ardine.storyapp.adapter.LoadingStateAdapter
import com.ardine.storyapp.adapter.StoryListAdapter
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.databinding.ActivityMainBinding
import com.ardine.storyapp.view.ViewModelFactory
import com.ardine.storyapp.view.camera.MediaActivity
import com.ardine.storyapp.view.maps.MapsActivity
import com.ardine.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private var token : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                token = user.token
                setupView(user.token)
                setupAction(user.token)
            }
        }
    }

    private fun setupAction(token: String) {
        val intent = Intent(this, MediaActivity::class.java)
        intent.putExtra(MediaActivity.EXTRA_TOKEN, token)

        binding.btnAdd.setOnClickListener{
            startActivity(intent)
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
        viewModel.getStory(token).observe(this@MainActivity){result ->
            if (result != null){
                when (result) {
                    ResultState.Loading -> {
                        binding.loadingProgressBar.isVisible = true
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.isVisible = false
                        Toast.makeText(this, getString(R.string.lost_connection) ,Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        binding.loadingProgressBar.isVisible = false
                        if (result.data.listStory.isNotEmpty()){
                            val adapter = StoryListAdapter(token)
                            binding.apply {
                                rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
                                rvStory.adapter = adapter.withLoadStateFooter(
                                    footer = LoadingStateAdapter{
                                        adapter.retry()
                                    },
                                )
                                viewModel.pagingStoryList(token).observe(this@MainActivity){
                                    adapter.submitData(lifecycle,it)
                                }
                            }
                        } else {
                            binding.apply {
                                rvStory.isVisible = false
                                noDataTextView.isVisible = true
                            }
                        }
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
            R.id.mapsButton ->{
                token?.let { tokenValue ->
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra(MapsActivity.EXTRA_TOKEN, tokenValue)
                    startActivity(intent)
                }
            }
            R.id.logoutButton -> {
                viewModel.logout()
                return true
            }
            R.id.translateButton -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}