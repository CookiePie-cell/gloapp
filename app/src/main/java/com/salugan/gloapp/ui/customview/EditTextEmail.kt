package com.salugan.gloapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.salugan.gloapp.R

class EditTextEmail : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        hint = context.getString(R.string.hint_email)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        isSingleLine = true
    }

    private fun init() {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        setBackgroundResource(R.drawable.edit_text_outline)
        setPadding(64, 48, 64, 48)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    error = if (!Patterns.EMAIL_ADDRESS.matcher(p0.trim()).matches()) {
                        setBackgroundResource(R.drawable.edit_text_outline_red)
                        context.getString(R.string.error_email)
                    } else {
                        setBackgroundResource(R.drawable.edit_text_outline)
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}