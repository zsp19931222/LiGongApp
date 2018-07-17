package com.example.app3.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.view.MyTitleView;
import yh.app.appstart.lg.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.logTool.Log;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/13.
 */

public class WebActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.web_title)
    MyTitleView webTitle;
    @BindView(R.id.web_webview)
    WebView webWebview;
    private LoadDiaog loadDiaog;

    private String title;
    private String url;

    private boolean aBoolean = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void setTitle(Context context) {
        if (title != null) {
            webTitle.setTitle(title, context);
            aBoolean = false;
        }
        webTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webWebview.canGoBack()) {
                    webWebview.canGoBack();
                    webWebview.goBack();// 返回前一个页面
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        title = getIntent().getExtras().getString("title");
        url = getIntent().getExtras().getString("url");
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
        WebSettings settings = webWebview.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        //settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        //settings.setUseWideViewPort(true);// 支持双击放大缩小
        webWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }

            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (aBoolean) {
                    webTitle.setTitle(title, WebActivity.this);
                }
                super.onReceivedTitle(view, title);
            }
        });
        webWebview.loadUrl(url);

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webWebview.canGoBack()) {  //表示按返回键
                        webWebview.goBack();   //后退
                        return true;    //已处理
                    } else {
                        finish();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    webWebview.destroy();
    }
}
