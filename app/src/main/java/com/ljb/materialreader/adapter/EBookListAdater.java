package com.ljb.materialreader.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.callback.IImageLoader;
import com.ljb.materialreader.utils.EBookUtils;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 书籍列表Adapter
 */

public class EBookListAdater extends BaseRvAdapter<BookDetail> {
    private int count;

    public EBookListAdater(List<BookDetail> datas, Context context, int count) {
        super(datas, R.layout.item_book_list, context);
        this.count = count;
    }

    public int getItemCountSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_EMPTY:
                return 1;
            default:
                return count;
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, BookDetail data) {
        float rating= 0 ;
        try {
            rating = Float.valueOf(data.getRetentionRatio())/20;
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setText(data.getTitle(), R.id.tv_book_title)//
                .setText(data.getLatelyFollower()+"人在追", R.id.tv_hots_num)//
                .setText(data.getBookInfoString(), R.id.tv_book_info)//
                .setText("\u3000" + data.getShortIntro(), R.id.tv_book_description);
        RatingBar ratingBar = holder.getView(R.id.rating_bat_hots);
        ratingBar.setRating(rating);
        holder.setImageView(EBookUtils.getImageUrl(data.getCover()), R.id.iv_book_img, new IImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String path) {
                Glide.with(mContext).load(path).into(imageView);
            }
        });
    }
}
