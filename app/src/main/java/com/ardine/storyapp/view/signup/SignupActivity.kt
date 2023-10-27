    package com.ardine.storyapp.view.signup
    
    import android.animation.AnimatorSet
    import android.animation.ObjectAnimator
    import android.os.Build
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.view.View
    import android.view.WindowInsets
    import android.view.WindowManager
    import androidx.activity.viewModels
    import androidx.appcompat.app.AlertDialog
    import androidx.core.view.isVisible
    import com.ardine.storyapp.R
    import com.ardine.storyapp.data.ResultState
    import com.ardine.storyapp.databinding.ActivitySignupBinding
    import com.ardine.storyapp.view.ViewModelFactory
    import com.google.android.material.textfield.TextInputLayout

    class SignupActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySignupBinding
        private val viewModel by viewModels<SignupViewModel> {
            ViewModelFactory.getInstance(this)
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySignupBinding.inflate(layoutInflater)
            setContentView(binding.root)
    
            playAnimation()
            setupView()
            binding.passwordEditText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().length < 8) {
                        binding.apply {
                            passwordEditText.setError(getString(R.string.error_msg_password),null)
                            passwordEditTextLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                        }
                        setSignupButton(false)
    
                    } else {
                        binding.apply {
                            passwordEditText.error = null
                            passwordEditTextLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                        }
                        setSignupButton(true)
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    setupAction()
                }
            })
    
        }
    
        private fun setSignupButton(isEnabled: Boolean) {
            binding.signupButton.isEnabled = isEnabled
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
            binding.signupButton.setOnClickListener {
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
    
                registerUser(name, email, password)
            }
        }
    
        private fun registerUser(name: String, email: String, password: String) {
            viewModel.register(name, email, password).observe(this){ result ->
                if (result != null){
                    when (result) {
                        ResultState.Loading -> {
                            binding.loadingProgressBar.isVisible = true
                        }

                        is ResultState.Error -> {
                            binding.loadingProgressBar.isVisible = false
                            showErrorDialog(getString(R.string.sign_up_failed))
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
                setTitle(getString(R.string.success))
                setMessage(message)
                setPositiveButton(getString(R.string.continue_txt)) { _, _ ->
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
            val nameEditTextLayout =
                ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
            val emailEditTextLayout =
                ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
            val passwordEditTextLayout =
                ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
            val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
    
    
            AnimatorSet().apply {
                playSequentially(
                    title,
                    nameEditTextLayout,
                    emailEditTextLayout,
                    passwordEditTextLayout,
                    signup
                )
                startDelay = 100
            }.start()
        }
    }