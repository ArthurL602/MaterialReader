package com.ljb.materialreader.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.utils.EBookUtils;

import java.util.List;

/**
 * Author      :meloon
 * Date        :2018/1/30
 * Description :
 */

public class EBookReviewsAdapter extends BaseRvAdapter<HotReview.Reviews> {

    public EBookReviewsAdapter(List<HotReview.Reviews> datas, Context context) {
        super(datas, R.layout.item_ebook_comments, context);
    }

    @Override
    protected void convert(BaseViewHolder holder, HotReview.Reviews data) {
        Glide.with(mContext).load(EBookUtils.getImageUrl(data.getAuthor().getAvatar())).into((ImageView) holder
                .getView(R.id.iv_avatar));
        holder.setText(data.getAuthor().getNickname(), R.id.tv_user_name)//
                .setText(data.getContent(), R.id.tv_comment_content)//
                .setText(data.getLikeCount() + "", R.id.tv_favorite_num)//
                .setText(data.getUpdated().split("T")[0], R.id.tv_update_time);
        AppCompatRatingBar ratingBar = holder.getView(R.id.rating_bat_hots);
        ratingBar.setRating(data.getRating());
    }
}
