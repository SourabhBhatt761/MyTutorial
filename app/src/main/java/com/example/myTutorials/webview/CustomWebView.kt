package com.example.myTutorials.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.flow.*

class CustomWebViewView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null
) : WebView(context, attrs) {

    var onUrlTokenFound: ((url: String?) -> Unit)? = null

    override fun loadUrl(url: String) {
        this.apply {
//            webViewClient = WebClient()
            tag = TAG
            settings.applyRequiredSetting()
//            webChromeClient = CustomWebChromeClient()
            super.loadData(url,"text/html","UTF-8")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebSettings.applyRequiredSetting() {
        javaScriptEnabled = true
//        userAgentString = USER_AGENT
        domStorageEnabled = true
        setSupportZoom(true)
        builtInZoomControls = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        displayZoomControls = true
        loadWithOverviewMode = true
        setSupportMultipleWindows(true)
        loadsImagesAutomatically = true
        allowFileAccess = true
        mediaPlaybackRequiresUserGesture = false
        javaScriptCanOpenWindowsAutomatically = true
        setLayerType(View.LAYER_TYPE_HARDWARE,null)
    }

    inner class WebClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            Log.i(TAG,"onReceivedError called ${error}")
            super.onReceivedError(view, request, error)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            val url = request.url.toString()
            if (url.contains("/auth/token")) {
                onUrlTokenFound?.invoke(url)
                return true
            }
            view.loadUrl(url)
            return true
        }
    }

    inner class CustomWebChromeClient : WebChromeClient() {
        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            val newWebView = WebView(view.context)
            val transport = resultMsg.obj as? WebViewTransport ?: return false
            newWebView.settings.applyRequiredSetting()
//            newWebView.layoutParams = this@CustomWebViewView.layoutParams
            newWebView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }
            }
            newWebView.webChromeClient = CustomWebChromeClient()
            transport.webView = newWebView
            resultMsg.sendToTarget()
            this@CustomWebViewView.addView(newWebView)
            return true
        }

//        override fun onCloseWindow(window: WebView?) {
//            super.onCloseWindow(window)
//            Log.i(TAG,"onCloseWindow called")
//            window?.let { webView ->
//                (webView.parent as? ViewGroup)?.removeView(webView)
//            }
//        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Log.i(TAG,"onConsoleMessage called ${consoleMessage}")
            return super.onConsoleMessage(consoleMessage)
        }
    }

    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Mobile Safari/537.36"

        private const val TAG = "CustomWebViewView"
    }
}