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
    import androidx.lifecycle.lifecycleScope
    import com.ardine.storyapp.R
    import com.ardine.storyapp.data.UserRepository
    import com.ardine.storyapp.data.di.Injection
    import com.ardine.storyapp.data.response.ErrorResponse
    import com.ardine.storyapp.databinding.ActivitySignupBinding
    import com.google.android.material.textfield.TextInputLayout
    import com.google.gson.Gson
    import kotlinx.coroutines.launch
    import retrofit2.HttpException

    class SignupActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySignupBinding
        private val viewModel: SignupViewModel by viewModels()
        private val userRepository: UserRepository = Injection.provideRepository(this)
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySignupBinding.inflate(layoutInflater)
            setContentView(binding.root)
    
    
            viewModel.loadingVisibility.observe(this) { visibility ->
                binding.loadingProgressBar.visibility = visibility
            }
    
            playAnimation()
            setupView()
            binding.passwordEditText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    
                }
    
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().length < 8) {
                        binding.apply {
                            passwordEditText.error = getString(R.string.error_msg_password)
                            passwordEditTextLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
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
                viewModel.showLoading()
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
    
                registerUser(name, email, password)
            }
        }
    
        private fun registerUser(name: String, email: String, password: String) {
            lifecycleScope.launch {
                try {
                    val registerResponse = userRepository.register(name, email, password)
                    val message: String? = registerResponse.message

                    if (message != null) {
                        viewModel.hideLoading()
                        showSuccessDialog(message)
                    } else {
                        viewModel.hideLoading()
                        val errorMessage = "Registration failed. Please try again."
                        showErrorDialog(errorMessage)
                    }

                }
                catch (e: HttpException) {
                    viewModel.hideLoading()
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message ?: "Email is already taken"
                    showErrorDialog(errorMessage)
                } catch (e: Exception) {
                    viewModel.hideLoading()
                    val errorMessage = "Registration failed. Please check your internet connection."
                    showErrorDialog(errorMessage)
                }
            }
        }
    
    
        private fun showSuccessDialog(message: String) {
            AlertDialog.Builder(this).apply {
                setTitle("Success!")
                setMessage(message)
                setPositiveButton("Continue") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    
        private fun showErrorDialog(message: String) {
            AlertDialog.Builder(this).apply {
                setTitle("Error!")
                setMessage(message)
                setPositiveButton("OK", null)
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
            viewModel.hideLoading()
        }
    }