package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BasePresenter;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/9
 * Description :
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.toobar)
    Toolbar mToobar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    private String mResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initToolbar();
        initData();
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(true);

        if(mResult!=null){
            mWebview.loadUrl(mResult);

        }
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    mProgress.setVisibility(View.GONE);
                } else {
                    if (mProgress.getVisibility() == View.GONE) {
                        mProgress.setVisibility(View.VISIBLE);
                    }
                    mProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mResult = intent.getStringExtra("result");
    }

    private void initToolbar() {
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }

    @Override
    protected void initEvent() {

    }



}
