package com.example.mybrower

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mybrower.databinding.ActivityWebPageBinding

internal class MyWebViewClient : WebViewClient() {
    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        handler?.proceed()
        super.onReceivedSslError(view, handler, error);
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view?.loadUrl(request?.url.toString())
        } else {
            view?.loadUrl(request.toString())
        }

        return true
    }
}

internal class MyWebChromeClient : WebChromeClient() {

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        return false
    }

    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean {
        return false
    }

    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        return super.onJsConfirm(view, url, message, result)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
    }
}

class WebPage : AppCompatActivity() {

    private lateinit var binding: ActivityWebPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webView.settings.javaScriptEnabled = true
//        webView.settings.mediaPlaybackRequiresUserGesture = false
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.builtInZoomControls = true
//        webView.settings.setSupportZoom(true)
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.domStorageEnabled = true
//        webView.settings.allowContentAccess = true
        binding.webView.settings.safeBrowsingEnabled = false
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.mixedContentMode = 0


        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = MyWebChromeClient()
        val url = intent.getStringExtra("url")
        if (url.isNullOrEmpty()) {
            return
        } else {
            binding.webView.loadUrl(url)
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
                binding.webView.destroy()
                return true
            } else {
                finish()
            }
        }

        if (keyCode == KeyEvent.KEYCODE_FORWARD) {
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
                binding.webView.destroy()
                return true
            } else {
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}