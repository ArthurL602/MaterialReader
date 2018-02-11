package com.ljb.materialreader.control;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.ljb.materialreader.R;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.ui.activity.EBookDetailActivity;
import com.ljb.materialreader.utils.EBookUtils;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description : 推荐书籍控制类
 */

public class EBookSeriesCeiController {
    private View mContentView;
    private ImageView iv_book_img;
    private TextView tv_title;
    private AppCompatRatingBar ratingBar_hots;
    private TextView tv_hots_num;
    private BookDetail tagBook;
    private Activity mActivity;

    public EBookSeriesCeiController(BookDetail tagBook, Activity activity) {
        this.tagBook = tagBook;
        mActivity = activity;
        initView();
        initEvent();
    }

    private void initView() {
        mContentView = LayoutInflater.from(mActivity).inflate(R.layout.item_book_series_ceil, null, false);
        iv_book_img = mContentView.findViewById(R.id.iv_book_img);
        tv_title = mContentView.findViewById(R.id.tv_book_title);
        ratingBar_hots =  mContentView.findViewById(R.id.rating_bat);
        tv_hots_num = mContentView.findViewById(R.id.tv_hots_num);
    }

    private void initEvent() {
        Glide.with(mActivity).load(EBookUtils.getImageUrl(tagBook.getCover())).into(iv_book_img);
        float ratio = 0;
        try {
            ratio = Float.parseFloat(tagBook.getRetentionRatio()) / 20;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_title.setText(tagBook.getTitle());
        ratingBar_hots.setRating(ratio);
        tv_hots_num.setText(tagBook.getLatelyFollower() + "人在追");
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putParcelable("BookDetail", tagBook);
                b.putString("bookId", tagBook.getId());
                Bitmap bitmap;
                GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) iv_book_img.getDrawable();
                if (imageDrawable != null) {
                    bitmap = imageDrawable.getBitmap();
                    b.putParcelable("book_img", bitmap);
                }
                Intent intent = new Intent(mActivity, EBookDetailActivity.class);
                intent.putExtras(b);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                        iv_book_img, "book_img");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mActivity.startActivity(intent, optionsCompat.toBundle());
                } else {
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    public View getContentView() {
        return mContentView;
    }

}
