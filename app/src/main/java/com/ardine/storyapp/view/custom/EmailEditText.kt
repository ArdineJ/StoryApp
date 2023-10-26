package com.ardine.storyapp.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.ardine.storyapp.R
import java.util.regex.Pattern

class EmailEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = if (s.isEmpty()) {
                    context.getString(R.string.required_fill)
                } else if (!isEmailValid(s.toString())) {
                    context.getString(R.string.invalid_email_format)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun isEmailValid(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }
}
