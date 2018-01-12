package com.ljb.materialreader.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hymane.expandtextview.ExpandTextView;
import com.ljb.materialreader.R;
import com.ljb.materialreader.app.App;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.bean.response.douban.BookReviewResponse;
import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;
import com.ljb.materialreader.bean.response.douban.BookSeriesListResponse;
import com.ljb.materialreader.callback.IImageLoader;
import com.ljb.materialreader.control.BookSeriesCeilController;
import com.ljb.materialreader.ui.activity.BookReviewsActivity;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description : 图书详情界面列表适配器
 */

public class BookDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public void setTotal(int total) {
        mReviewsListResponse.setTotal(total);
    }

    /**
     * 图书信息
     */
    public static final int TYPE_BOOK_INFO = 0;
    /**
     * 图书简介
     */
    public static final int TYPE_BOOK_BRIEF = 1;
    /**
     * 图书评论
     */
    public static final int TYPE_BOOK_COMMENT = 2;
    /**
     * 推荐书籍
     */
    public static final int TYPE_BOOK_RECOMMENT = 3;
    /**
     * 头部数量
     */
    public static final int HEADER_COUNT = 2;
    //模拟加载时间
    private static final int PROGRESS_DELAY_MIN_TIME = 500;
    private static final int PROGRESS_DELAY_SIZE_TIME = 1000;
    private BookInfoResponse mBookInfo;
    private BookReviewsListResponse mReviewsListResponse;
    private BookSeriesListResponse mSeriesListResponse;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private StringBuilder mBuilder;

    public BookReviewsListResponse getReviewsListResponse() {
        return mReviewsListResponse;
    }

    public BookSeriesListResponse getSeriesListResponse() {
        return mSeriesListResponse;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BookDetailAdapter(BookInfoResponse bookInfo, BookReviewsListResponse reviewsListResponse,
                             BookSeriesListResponse seriesListResponse, Context context) {
        mBookInfo = bookInfo;
        mReviewsListResponse = reviewsListResponse;
        mSeriesListResponse = seriesListResponse;
        mContext = context;
    }

    /**
     * 图书出版信息是否打开
     */

    private boolean flag;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_BOOK_INFO://信息
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_info, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_BOOK_BRIEF://简介
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_brief, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_BOOK_COMMENT://评论
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_common, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_BOOK_RECOMMENT://推荐书籍
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_series, parent, false);
                holder = new BaseViewHolder(view);
                break;
        }
        return holder;
    }

    public StringBuilder getBuilder() {
        return mBuilder;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_BOOK_INFO://信息
                AppCompatRatingBar ratingBar = holder.getView(R.id.rating_bar);
                ratingBar.setRating(Float.valueOf(mBookInfo.getRating().getAverage()) / 2);
                holder.setText(mBookInfo.getRating().getAverage(), R.id.tv_hots_num)//
                        .setText(mBookInfo.getRating().getNumRaters() + "人评论", R.id.tv_comment_num)//
                        .setText(mBookInfo.getInfoString(), R.id.tv_more_info);

                //详细    信息
                mBuilder = new StringBuilder();
                if (mBookInfo.getAuthor().length > 0) {
                    mBuilder.append("作者：").append(mBookInfo.getAuthor()[0]).append("\n");
                }
                mBuilder.append("出版社：").append(mBookInfo.getPublisher()).append("\n");
                mBuilder.append("副标题:").append(mBookInfo.getSubtitle()).append("\n");
                mBuilder.append("原作名:").append(mBookInfo.getOrigin_title()).append("\n");
                if (mBookInfo.getTranslator().length > 0) {
                    mBuilder.append("译者:").append(mBookInfo.getTranslator()[0]).append("\n");
                }

                mBuilder.append("出版年:").append(mBookInfo.getPubdate()).append("\n");
                mBuilder.append("页数:").append(mBookInfo.getPages()).append("\n");
                mBuilder.append("定价:").append(mBookInfo.getPrice()).append("\n");
                mBuilder.append("装帧:").append(mBookInfo.getBinding()).append("\n");
                mBuilder.append("isbn:").append(mBookInfo.getIsbn13()).append("\n");

                //注册点击事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener == null) return;
                        if (flag) {
                            ObjectAnimator.ofFloat(holder.getView(R.id.iv_more_info), "rotation", 90, 0).start();
                            flag = false;
                        } else {
                            ObjectAnimator.ofFloat(holder.getView(R.id.iv_more_info), "rotation", 0, 90).start();
                            flag = true;
                        }
                        mOnItemClickListener.onItemClick(v, holder, BookDetailAdapter.this);
                    }
                });
                break;
            case TYPE_BOOK_BRIEF://简介
                ExpandTextView textView = holder.getView(R.id.etv_brief);
                if (!mBookInfo.getSummary().isEmpty()) {

                    textView.setContent(mBookInfo.getSummary());
                } else {
                    textView.setContent("暂无");
                }
                break;
            case TYPE_BOOK_COMMENT://评论
                List<BookReviewResponse> reviews = mReviewsListResponse.getReviews();
                Log.e("TAG", "position: " + position);
                if (reviews.isEmpty()) {
                    holder.itemView.setVisibility(View.GONE);
                } else if (position == HEADER_COUNT) {
                    TextView tv = holder.getView(R.id.tv_comment_title);
                    tv.setVisibility(View.VISIBLE);
                } else if (position == reviews.size() + 1) {//更多评论
                    holder.getView(R.id.tv_more_comment).setVisibility(View.VISIBLE);
                    holder.setText("更多评论" + mReviewsListResponse.getTotal() + "条", R.id.tv_more_comment);
                    holder.getView(R.id.tv_more_comment).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //更多评论
                            Intent intent = new Intent(mContext, BookReviewsActivity.class);
                            intent.putExtra("bookId", mBookInfo.getId());
                            intent.putExtra("bookName", mBookInfo.getTitle());
                            mContext.startActivity(intent);
                        }
                    });
                }

                //
                int pos = position - HEADER_COUNT;
                BookReviewResponse review = reviews.get(pos);
                holder.setImageView(review.getAuthor().getAvatar(), R.id.iv_avatar, new IImageLoader() {
                    @Override
                    public void loadImage(ImageView imageView, String path) {
                        Glide.with(App.getContext()).load(path).asBitmap().centerCrop().into(imageView);
                    }
                });
                holder.setText(review.getAuthor().getName(), R.id.tv_user_name)//
                        .setText(review.getSummary(), R.id.tv_comment_content)//
                        .setText(review.getVotes() + "", R.id.tv_favorite_num)//
                        .setText(review.getUpdated().split(" ")[0], R.id.tv_update_time);
                if (review.getRating() != null) {
                    AppCompatRatingBar rating = holder.getView(R.id.rating_bar);
                    rating.setRating(Float.valueOf(review.getRating().getValue()));
                }
                break;
            case TYPE_BOOK_RECOMMENT://推荐书籍
                List<BookInfoResponse> series = mSeriesListResponse.getBooks();
                if (series.isEmpty()) {
                    holder.itemView.setVisibility(View.GONE);
                } else {
                    BookSeriesCeilController controller;
                    for (int i = 0; i < series.size(); i++) {
                        controller = new BookSeriesCeilController(series.get(i));
                        ((LinearLayout) holder.getView(R.id.ll_series_content)).addView(controller.getContentView());
                    }
                }
                break;
        }
    }

    public void addReviews(List<BookReviewResponse> datas) {
        mReviewsListResponse.getReviews().addAll(datas);
        notifyDataSetChanged();
    }

    public void addSeries(List<BookInfoResponse> datas) {
        mSeriesListResponse.getBooks().addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = HEADER_COUNT;
        if (mReviewsListResponse != null) {//如果评论不为空
            count += mReviewsListResponse.getReviews().size();
        }
        if (mSeriesListResponse != null && !mSeriesListResponse.getBooks().isEmpty()) {//如果推荐书为不空
            count += 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {//第一位 图书的信息
            return TYPE_BOOK_INFO;
        } else if (position == 1) {//第二位 图书的简介
            return TYPE_BOOK_BRIEF;
        } else if (position > 1 && position < (mReviewsListResponse == null ? HEADER_COUNT : (mReviewsListResponse
                .getReviews().size() + HEADER_COUNT))) {//判断类型是否是评论类型（position > 1）
            return TYPE_BOOK_COMMENT;
        } else {
            return TYPE_BOOK_RECOMMENT;//推荐书籍
        }

    }

    //点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, BaseViewHolder holder, BookDetailAdapter adapter);
    }
}
