package com.neutron.mexicoloan.ui

import android.net.Uri
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.neutron.baselib.base.IBaseActivity
import com.neutron.baselib.utils.BaseConstant
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : IBaseActivity() {

    override fun getLayoutId(): Int {
       return R.layout.activity_web_view
    }

    override fun initBaseView() {
        val intent = intent
        if (intent != null) {
         val uri=   intent.getStringExtra(BaseConstant.Intent_URI)

            uri?.let {

                if(it.contains("http")){
                    loadURI(it)
                    fl_main.visibility=View.VISIBLE
                    tv_message.visibility=View.GONE

                }else{
                    tv_message.visibility=View.VISIBLE
                    fl_main.visibility=View.GONE
                    tv_message.text=it
                }

            }




        }
    }

    var mAgentWeb: AgentWeb? = null
    private fun loadURI(url: String) {

        Slog.d("loadURI  ")
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                fl_main,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
//            .setWebViewClient(mWebViewClient)
            .setWebChromeClient(mWebViewClient)
            .createAgentWeb()

            .ready()
            .go(url)

        Slog.d("访问  $url")
        //优先使用网络
        val webViewSetting = mAgentWeb?.webCreator?.webView?.settings
        webViewSetting?.cacheMode = WebSettings.LOAD_DEFAULT
        //将图片调整到适合webview的大小
        webViewSetting?.useWideViewPort = true
        //隐藏缩放按钮
        webViewSetting?.displayZoomControls = false
        //支持内容重新布局
        webViewSetting?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //支持自动加载图片
        webViewSetting?.loadsImagesAutomatically = true
        //当webview调用requestFocus时为webview设置节点
        webViewSetting?.setNeedInitialFocus(true)
        //自适应屏幕
        webViewSetting?.useWideViewPort = true
        webViewSetting?.javaScriptEnabled = true
        webViewSetting?.loadWithOverviewMode = true
        //开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 javascript 来操作这些数据。）
        webViewSetting?.domStorageEnabled = true
        //支持缩放
        webViewSetting?.builtInZoomControls = true
        webViewSetting?.setSupportZoom(true)
        //允许webview对文件的操作
        webViewSetting?.allowFileAccess = true
        webViewSetting?.allowFileAccessFromFileURLs = true
//        2021年3月22日 安全检测 修改为false
        webViewSetting?.allowUniversalAccessFromFileURLs = true
        mAgentWeb?.webCreator?.webView
            ?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mAgentWeb!!.webCreator
                            .webView.canGoBack()
                    ) { // 表示按返回键时的操作
                        mAgentWeb?.webCreator!!.webView.goBack() // 后退
                        // webview.goForward();//前进
                        return@OnKeyListener true // 已处理
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        moveTaskToBack(true);
                        finish()
                    }
                }
                false
            })


    }

    val mWebViewClient: com.just.agentweb.WebChromeClient =
        object : com.just.agentweb.WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {



                return true

            }

        }


}