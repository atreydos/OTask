package io.atreydos.otask.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView

@SuppressLint("ViewConstructor")
class TrendDetailedWebView(context: Context, url: String?) : WebView(context) {

    init {
        this.loadUrl(url)
    }
}