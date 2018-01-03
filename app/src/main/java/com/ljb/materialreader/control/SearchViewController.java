package com.ljb.materialreader.control;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.utils.AnimationUtils;
import com.ljb.materialreader.utils.KeyBoardUtils;

import static com.ljb.materialreader.R.id.et_search_content;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description : 搜索View的控制类
 */

public class SearchViewController implements View.OnClickListener {
    //搜索框为空
    public static final int RESULT_SEARCH_EMPTY_KEYWORD = 0;
    //搜索
    public static final int RESULT_SEARCH_SEARCH = 1;
    //扫描
    public static final int RESULT_SEARCH_GO_SCAN = 2;
    //返回
    public static final int RESULT_SEARCH_CANCEL = 3;

    private Activity mContext;
    private View mContentView;
    private OnSearchHandlerListener mListener;
    private ImageView mIv_back;
    private ImageView mIv_scan;
    private ImageView mIv_fork_clear;
    private ImageView mIv_search;
    private EditText mEtContent;

    public SearchViewController(Activity context, OnSearchHandlerListener listener) {
        mContext = context;
        mListener = listener;
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_search, null);

        initView();
        initEvent();
    }

    private void initEvent() {
        mIv_back.setOnClickListener(this);
        mIv_scan.setOnClickListener(this);
        mIv_fork_clear.setOnClickListener(this);
        mIv_search.setOnClickListener(this);

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mIv_fork_clear.setVisibility(View.VISIBLE);

                } else {
                    mIv_fork_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mEtContent.getText().toString().isEmpty()) {
                        //如果输入框是空的，则显示晃动动画
                        if (mListener != null) {
                            mEtContent.startAnimation(AnimationUtils.shakeAnimation(5));
                            mListener.onSearch(RESULT_SEARCH_EMPTY_KEYWORD);
                        }
                        return false;
                    }

                    if (mListener != null) {
                        KeyBoardUtils.closeKeyBord(mEtContent, mContext);
                        mListener.onSearch(RESULT_SEARCH_SEARCH);

                    }
                    return true;
                }
                return false;
            }
        });
    }

    public View getContentView() {
        return mContentView;
    }

    private void initView() {
        mIv_back = mContentView.findViewById(R.id.iv_arrow_back);
        mIv_scan = mContentView.findViewById(R.id.iv_scan);
        mIv_fork_clear = mContentView.findViewById(R.id.iv_fork_clear);
        mIv_search = mContentView.findViewById(R.id.iv_search);
        mEtContent = mContentView.findViewById(et_search_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                KeyBoardUtils.closeKeyBord(mEtContent, mContext);
                if (mListener != null) {
                    mListener.onSearch(RESULT_SEARCH_CANCEL);
                }
                break;
            case R.id.iv_scan:
                if (mListener != null) {
                    mListener.onSearch(RESULT_SEARCH_GO_SCAN);
                }
                break;
            case R.id.iv_fork_clear:
                mEtContent.setText("");
                break;
            case R.id.iv_search:
                if (mEtContent.getText().toString().isEmpty()) {
                    //如果输入框是空的，则显示晃动动画
                    if (mListener != null) {
                        mEtContent.startAnimation(AnimationUtils.shakeAnimation(5));
                        mListener.onSearch(RESULT_SEARCH_EMPTY_KEYWORD);
                    }
                    break;
                }

                if (mListener != null) {
                    KeyBoardUtils.closeKeyBord(mEtContent, mContext);
                    mListener.onSearch(RESULT_SEARCH_SEARCH);

                }
                break;
        }
    }

    public EditText getEtContent() {
        return mEtContent;
    }

    public interface OnSearchHandlerListener {
        void onSearch(int code);
    }

    public void onDestroy() {
        mContentView = null;
        mContext = null;
    }
}
