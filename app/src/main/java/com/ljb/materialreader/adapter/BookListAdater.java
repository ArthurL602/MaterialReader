package com.ljb.materialreader.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.callback.IImageLoader;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 书籍列表Adapter
 */

public class BookListAdater extends BaseRvAdapter<BookInfoResponse> {
    private int count;

    public BookListAdater(List<BookInfoResponse> datas, Context context, int count) {
        super(datas, R.layout.item_book_list, context);
        this.count = count;
    }

    @Override
    protected void convert(BaseViewHolder holder, BookInfoResponse data) {
        holder.setText(data.getTitle(), R.id.tv_book_title)//
                .setText(data.getRating().getAverage(), R.id.tv_hots_num)//
                .setText(data.getInfoString(), R.id.tv_book_info)//
                .setText(data.getSummary(), R.id.tv_book_description);
        RatingBar ratingBar = holder.getView(R.id.rating_bat_hots);
        ratingBar.setRating(Float.valueOf(data.getRating().getAverage()) / 2);
        holder.setImageView(data.getImages().getLarge(), R.id.iv_book_img, new IImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String path) {
                Glide.with(mContext).load(path).into(imageView);
            }
        });
    }

    public int getItemCountSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_EMPTY:
                return 1;
            default:
                return count;
        }
    }
}
