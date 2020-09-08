package io.atreydos.otask.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast


@SuppressLint("ViewConstructor")
class TrendDetailedWebView(context: Context, url: String?) : WebView(context) {

    private class InjectedAppJSInterface(private val context: Context) {

        companion object {
            const val INTERFACE_NAME = "InjectedApp"
        }

        @JavascriptInterface
        fun ShowToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private val mWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, newUrl: String?): Boolean {
            if (Uri.parse(newUrl).host == Uri.parse(url).host) {
                // Навигация происходит в пределах домена - оверрайдить не нужно,
                // позволить WebView загрузить страницу
                return false
            }
            // Иначе, попросить OS Android отхэндлить URL
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newUrl)))
            return true
        }

    }

    init {
        @SuppressLint("SetJavaScriptEnabled")
        settings.javaScriptEnabled = true
        addJavascriptInterface(
            InjectedAppJSInterface(context),
            InjectedAppJSInterface.INTERFACE_NAME
        )
        webViewClient = mWebViewClient
        loadUrl(url)
    }

}