package com.ljb.materialreader.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.ljb.materialreader.R;
import com.ljb.materialreader.app.App;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.ui.activity.BookDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ljb.materialreader.R.id.iv_book_img;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description : 推荐书籍控制类
 */

public class BookSeriesCeilController {

    @BindView(iv_book_img)
    ImageView mIvBookImg;
    @BindView(R.id.tv_book_title)
    TextView mTvBookTitle;
    @BindView(R.id.rating_bat)
    AppCompatRatingBar mRatingBat;
    @BindView(R.id.tv_hots_num)
    TextView mTvHotsNum;
    private BookInfoResponse mBookInfoResponse;
    private Unbinder mUnbinder;
    private View mContentView;

    public BookSeriesCeilController(BookInfoResponse bookInfoResponse) {
        mBookInfoResponse = bookInfoResponse;
        initView();
        initEvent();
    }

    public View getContentView() {
        return mContentView;
    }

    private void initView() {
        mContentView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_book_series_ceil, null);
        mUnbinder = ButterKnife.bind(this, mContentView);

        Glide.with(App.getContext()).load(mBookInfoResponse.getUrl()).into(mIvBookImg);

        mTvBookTitle.setText(mBookInfoResponse.getTitle());
        mTvHotsNum.setText(mBookInfoResponse.getRating().getAverage());
        mRatingBat.setRating(Float.valueOf(mBookInfoResponse.getRating().getAverage()) / 2);

    }

    private void initEvent() {
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable(BookInfoResponse.serialVersionName, mBookInfoResponse);
                Bitmap bitmap;

                bitmap = ((GlideBitmapDrawable) (mIvBookImg.getDrawable())).getBitmap();
                b.putParcelable("book_img", bitmap);
                Intent intent = new Intent(App.getContext(), BookDetailActivity.class);
                intent.putExtras(b);
            }
        });
    }

}
