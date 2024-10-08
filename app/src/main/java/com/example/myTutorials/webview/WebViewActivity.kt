package com.example.myTutorials.webview

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService.KEY_URL
import androidx.browser.customtabs.CustomTabsServiceConnection
import com.example.myTutorials.databinding.ActivityWebviewBinding


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private val TAG = "WebViewSAMLCode"
    private val url = "https://pages.talview.com/saml"
    private val html = "<video class=\"ql-video ql-align-center\" src=\"https://static.talview.com/bestbuy/TalviewVideoInterviewPlatform-Q1.mp4\" height=\"187\" width=\"100%\" controls></video><p><br></p><p><strong>Why are you excited about working at Best Buy?</strong></p><p><br></p>"
//    private val html = "<meta http-equiv=\"Content-Security-Policy\" content=\"default-src 'self' https://static.talview.com/;\">\n" +
//        "<video class=\"ql-video ql-align-center\" src=\"https://static.talview.com/bestbuy/TalviewVideoInterviewPlatform-Q1.mp4\" height=\"187\" width=\"100%\" controls></video><p><br></p><p><strong>Why are you excited about working at Best Buy?</strong></p><p><br></p>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.onUrlTokenFound = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(html)
        Log.i("ActivityB","onCreate() called")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ActivityB","onRestart() called")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ActivityB","onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ActivityB","onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ActivityB","onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ActivityB","onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ActivityB","onDestroy() called")
    }

    private fun chromeCustomTab() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

        CustomTabsClient.bindCustomTabsService(
            this,
            applicationContext.packageName,
            object : CustomTabsServiceConnection() {

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.i(TAG, "onServiceDisconnected called")
                }

                override fun onCustomTabsServiceConnected(
                    name: ComponentName,
                    client: CustomTabsClient
                ) {
                    Log.i(TAG, "onCustomTabsServiceConnected called")
                    client.warmup(0)
                    client.newSession(object : CustomTabsCallback() {
                        override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                            Log.i(TAG, "onNavigationEvent called")
                            val url = extras?.getString(KEY_URL)
                            Log.d("CustomTabUrl", "URL visited: $url")
                        }
                    })
                }
            })
        return
    }
}