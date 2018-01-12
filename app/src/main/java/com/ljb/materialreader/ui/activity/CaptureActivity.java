package com.ljb.materialreader.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BasePresenter;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :扫描界面
 */

public class CaptureActivity extends BaseActivity {

    private CaptureFragment mCaptureFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        mCaptureFragment = new CaptureFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mCaptureFragment).commit();
    }

    @Override
    protected void initEvent() {
        mCaptureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {//扫描成功
                Log.e("TAG", "result： " + result);
                Intent intent = null;
                if (result.startsWith("http")) {
                    intent = new Intent(CaptureActivity.this, WebViewActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                    finish();
                }else{
                    intent = new Intent(CaptureActivity.this,SearchResultActivity.class);
                    intent.putExtra("q",result);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnalyzeFailed() {//扫描失败

            }
        });
    }
}
