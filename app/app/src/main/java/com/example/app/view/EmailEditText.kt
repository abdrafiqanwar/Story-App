package com.example.app.view

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            error = null
        } else {
            setError("Format email salah", null)
        }
    }
}