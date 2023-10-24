package com.ardine.storyapp.view.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ardine.storyapp.R
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.databinding.ActivityDetailBinding
import com.ardine.storyapp.view.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        val token = intent.getStringExtra(EXTRA_TOKEN)
        if (id != null && token != null) {
            setupView(token,id)
        }
    }

    private fun setupView(token: String, id: String ) {
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

        viewModel.getDetailStory(token,id).observe(this){result ->
            if (result != null){
                when (result) {
                    ResultState.Loading -> {
                        binding.loadingProgressBar.isVisible = true
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.isVisible = false
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        binding.apply {
                            loadingProgressBar.isVisible = false
                            tvTitle.text = result.data.story.name
                            tvDesc.text = result.data.story.description

                            Glide.with(this@DetailActivity)
                                .load(result.data.story.photoUrl)
                                .error(R.drawable.image_dicoding)
                                .into(ivStory)

                        }
                    }
                }
            }
        }
    }

    companion object{
        const val EXTRA_TOKEN = "extra_token"
        const val EXTRA_ID = "extra_id"
    }
}