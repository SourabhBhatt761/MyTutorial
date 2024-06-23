package com.example.myTutorials.webview

import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService.KEY_URL
import androidx.browser.customtabs.CustomTabsServiceConnection
import com.example.myTutorials.R
import com.example.myTutorials.databinding.ActivityWebviewBinding
import com.example.myTutorials.databinding.CustomDialogWebviewBinding


class WebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private val TAG = "WebViewSAMLCode"
    private val url = "https://pages.talview.com/saml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        chromeCustomTab()
        
        binding.webView.settings.apply {
            javaScriptEnabled = true
//            setSupportZoom(false)

            /* onConsoleMessage() is mandatory to override inside weChromeClient if you have enabled this feature*/
            setSupportMultipleWindows(true)
        }

        binding.webView.webChromeClient = object : WebChromeClient() {

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                Log.i(TAG,"onConsoleMessage -> ${consoleMessage!!.message()}")
                return true
            }

            override fun onCloseWindow(window: WebView?) {
                Log.i(TAG,"onCloseWindow called")
                super.onCloseWindow(window)
            }

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message
            ): Boolean {

                Log.i(TAG,"onCreateWindow called")
                val newWebView = WebView(this@WebviewActivity).apply {
                    settings.javaScriptEnabled = true
                    settings.setSupportMultipleWindows(true)
                    settings.useWideViewPort = true
                }

//                val dialogView = CustomDialogWebviewBinding.inflate(layoutInflater)
//                val dialog = AlertDialog.Builder(view!!.context).setView(dialogView.root).create()
//                val dialogWebView = dialogView.dialogWebView
//                dialogWebView.apply {
//                    settings.javaScriptEnabled = true
//                    settings.setSupportMultipleWindows(true)
//                    settings.javaScriptCanOpenWindowsAutomatically = true
//                    settings.useWideViewPort = true
//                    isFocusable = true
//                    isFocusableInTouchMode = true
//                    requestFocus(View.FOCUS_DOWN)
//                }

//                dialogWebView.webViewClient = object : WebViewClient() {
//                    override fun shouldOverrideUrlLoading(
//                        view: WebView?,
//                        request: WebResourceRequest?
//                    ): Boolean {
//
//                        Log.i(TAG, "dialogOverrideURL -> ${request?.url} view -> $view")
//                        return false
//                    }
//                }

                newWebView.webViewClient = object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        url: String
                    ): Boolean {
                        view?.loadUrl(url)
                        Log.i(TAG, "chromeOverrideURL -> $url view -> $view")


//                        val builder = CustomTabsIntent.Builder()
//                        val customTabsIntent = builder.build()
//                        customTabsIntent.launchUrl(view!!.context, Uri.parse(url))

//                        dialogWebView.loadUrl(url)
//                        dialog.show()

                        view!!.webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                Log.i(TAG, "dialogOverrideURL -> ${request?.url} view -> $view")
                                return false
                            }
                        }


                        return false
                    }

                    override fun doUpdateVisitedHistory(
                        view: WebView?,
                        url: String?,
                        isReload: Boolean
                    ) {
                        Log.i(TAG,"chromeHistory called $url")
                        super.doUpdateVisitedHistory(view, url, isReload)
                    }
                }

                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                view!!.addView(newWebView)
                resultMsg.sendToTarget()

//                dialogView.closeButton.setOnClickListener {
//                    dialog.dismiss()
//                    newWebView.destroy()
//                }

                return true // Indicate that we've handled the new window
            }
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
//                view!!.loadUrl(url)
                Log.i(TAG, "webViewClient url-> $url view -> $view")
                return false
            }

            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                Log.i(TAG,"clientHistory called $url")
                super.doUpdateVisitedHistory(view, url, isReload)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.v(TAG,"onPageStarted called $url")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.v(TAG,"onPageFinished called $url")
                super.onPageFinished(view, url)
            }
        }

        binding.webView.loadUrl("https://pages.talview.com/saml")

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun chromeCustomTab() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

        CustomTabsClient.bindCustomTabsService(this,applicationContext.packageName,object : CustomTabsServiceConnection(){

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i(TAG,"onServiceDisconnected called")
            }

            override fun onCustomTabsServiceConnected(
                name: ComponentName,
                client: CustomTabsClient
            ) {
                Log.i(TAG,"onCustomTabsServiceConnected called")
                client.warmup(0)
                client.newSession(object : CustomTabsCallback(){
                    override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                        Log.i(TAG,"onNavigationEvent called")
                        val url = extras?.getString(KEY_URL)
                        Log.d("CustomTabUrl", "URL visited: $url")
                    }
                })
            }
        })
        return
    }
}