package com.ljb.materialreader.ui.activity;


import android.graphics.Bitmap;

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
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
    }
}
