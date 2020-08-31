package io.atreydos.otask.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

@SuppressLint("ViewConstructor")
class TrendDetailedText(context: Context, text: String?) : AppCompatTextView(context) {

    init {
        this.gravity = Gravity.CENTER
        this.text = text
    }
}