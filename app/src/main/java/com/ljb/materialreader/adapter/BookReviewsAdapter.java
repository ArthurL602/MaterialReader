package com.ljb.materialreader.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.ImageView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.douban.BookReviewResponse;
import com.ljb.materialreader.callback.IImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Author      :meloon
 * Date        :2018/1/5
 * Description :
 */

public class BookReviewsAdapter extends BaseRvAdapter<BookReviewResponse> {
    public BookReviewsAdapter(List<BookReviewResponse> datas, Context context) {
        super(datas, R.layout.item_book_common, context);
    }

    @Override
    protected void convert(BaseViewHolder holder, BookReviewResponse data) {
        holder.setImageView(data.getAuthor().getAvatar(), R.id.iv_avatar, new IImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String path) {
                Picasso.with(mContext).load(path).into(imageView);
            }
        });

        holder.setText(data.getAuthor().getName(), R.id.tv_user_name)//
                .setText(data.getSummary(), R.id.tv_comment_content)//
                .setText(data.getRating().getValue(), R.id.tv_favorite_num)//
                .setText(data.getUpdated().split(" ")[0], R.id.tv_update_time);
        if (data.getRating() != null) {
            AppCompatRatingBar ratingBar = holder.getView(R.id.rating_bar);
            ratingBar.setRating(Float.valueOf(data.getRating().getValue()) / 2);
        }

    }

    public void addNewData(List<BookReviewResponse> datas) {
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

    public void addLoadMoreData(List<BookReviewResponse> datas) {
        int size = getDatas().size();
        getDatas().addAll(datas);
        notifyItemInserted(size);
    }
}
