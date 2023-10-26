package com.ardine.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ardine.storyapp.R
import com.ardine.storyapp.data.ResultState
import com.ardine.storyapp.databinding.ActivityLoginBinding
import com.ardine.storyapp.view.ViewModelFactory
import com.ardine.storyapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.toString().isEmpty()) {
                setLoginButton(false)
            } else {
                setLoginButton(true)
            }
        }

        override fun afterTextChanged(s: Editable) {
            setupAction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        setupView()
        playAnimation()
    }

    private fun setLoginButton(isEnabled: Boolean) {
        binding.loginButton.isEnabled = isEnabled
    }
    private fun setupView() {
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
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            loginUser(email,password)
        }
    }

    private fun loginUser(email: String, password: String ){
        viewModel.login(email, password).observe(this){ result ->
            if (result != null){
                when (result) {
                    ResultState.Loading -> {
                        binding.loadingProgressBar.isVisible = true
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.isVisible = false
                        showErrorDialog(getString(R.string.login_failed))
                    }

                    is ResultState.Success -> {
                        binding.loadingProgressBar.isVisible = false
                        showSuccessDialog(result.data.message)
                    }
                }
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.login_succeed))
            setMessage(message)
            setPositiveButton(getString(R.string.continue_txt)) { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.failed))
            setMessage(message)
            setPositiveButton(getString(R.string.retry), null)
            create()
            show()
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailEditTextLayout,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

}