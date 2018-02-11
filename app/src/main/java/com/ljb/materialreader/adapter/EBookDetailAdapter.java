package com.ljb.materialreader.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.view.FlowLayout;
import com.hymane.expandtextview.ExpandTextView;
import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.bean.response.ebook.LikedBookList;
import com.ljb.materialreader.constant.Constant;
import com.ljb.materialreader.control.EBookSeriesCeiController;
import com.ljb.materialreader.ui.activity.EBookReviewsActivity;
import com.ljb.materialreader.ui.activity.EBookZoneActivity;
import com.ljb.materialreader.ui.activity.ESearchResultActivity;
import com.ljb.materialreader.utils.DensityUtil;
import com.ljb.materialreader.utils.EBookUtils;

import java.util.List;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description : EBook详情页面适配器
 */

public class EBookDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    //书籍消息
    public static final int TYPE_BOOK_INFO = 0;
    //书籍简介
    public static final int TYPE_BOOK_BRIEF = 1;
    //书籍评论
    public static final int TYPE_BOOK_COMMENT = 2;
    //
    public static final int TYPE_BOOK_RECOMMENT = 3;
    //推荐书籍列表
    public static final int TYPE_BOOK_LIST = 4;

    private HotReview mHotReview;
    private LikedBookList mLikedBookList;
    private BooksByTag mBooksByTag;
    private BookDetail mBookDetail;
    public static final int HEADER_COUNT = 2;
    private final LayoutInflater mInflater;
    private boolean flag;
    private Activity mActivity;

    public EBookDetailAdapter(Activity activity, BookDetail bookDetail, HotReview hotReview, LikedBookList
            likedBookList, BooksByTag booksByTag) {
        mActivity = activity;
        mBookDetail = bookDetail;
        mInflater = LayoutInflater.from(mActivity);
        mHotReview = hotReview;
        mLikedBookList = likedBookList;
        mBooksByTag = booksByTag;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        BaseViewHolder holder;
        switch (viewType) {
            case TYPE_BOOK_INFO://
                view = mInflater.inflate(R.layout.item_ebook_info, parent, false);
                return new BaseViewHolder(view);
            case TYPE_BOOK_BRIEF://简介
                view = mInflater.inflate(R.layout.item_book_brief, parent, false);
                return new BaseViewHolder(view);
            case TYPE_BOOK_COMMENT://评论
                view = mInflater.inflate(R.layout.item_ebook_comments, parent, false);
                return new BaseViewHolder(view);
            case TYPE_BOOK_RECOMMENT:
                view = mInflater.inflate(R.layout.item_book_series, parent, false);
                return new BaseViewHolder(view);
            default:
                view = mInflater.inflate(R.layout.item_ebook_list, parent, false);
                return new BaseViewHolder(view);
        }
    }

    public void setBookDetail(BookDetail bookDetail) {
        mBookDetail = bookDetail;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_BOOK_INFO:
                float ratio = 0;
                ratio = Float.parseFloat(mBookDetail.getRetentionRatio()) / 20;
                ratio = (float) (Math.round(ratio * 100)) / 100;
                AppCompatRatingBar ratingBar = holder.getView(R.id.rating_bat_hots);
                ratingBar.setRating(ratio);
                //设置文本
                holder.setText(ratio + "", R.id.tv_hots_num)//
                        .setText(mBookDetail.getWordCount() / 10000 + "万字", R.id.tv_words_num)//
                        .setText(mBookDetail.getBookInfoString(), R.id.tv_book_info);
                final StringBuilder sb = new StringBuilder();
                sb.append("作者：").append(mBookDetail.getAuthor()).append("\n");
                sb.append("追书人数：").append(mBookDetail.getLatelyFollower()).append("\n");
                sb.append("读者留存率：").append(mBookDetail.getRetentionRatio()).append("%").append("\n");
                sb.append("日更新字数：").append(mBookDetail.getSerializeWordCount()).append("\n");
                sb.append("总章节数：").append(mBookDetail.getChaptersCount()).append("\n");
                sb.append("更新时间：").append(mBookDetail.getUpdated()).append("\n");
                sb.append("最新章节：").append(mBookDetail.getLastChapter()).append("\n");
                sb.append("是否连载：").append(mBookDetail.isSerial() ? "是" : "否").append("\n");
                sb.append("次级分类：").append(mBookDetail.getMajorCate()).append("\n");
                sb.append("Creater：").append(mBookDetail.getCreater()).append("\n");
                final ImageView iv_more_info = holder.getView(R.id.iv_more_info);
                holder.getView(R.id.rl_more_info).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            ObjectAnimator.ofFloat(iv_more_info, "rotation", 90, 0).start();
                            flag = false;
                        } else {
                            ObjectAnimator.ofFloat(iv_more_info, "rotation", 0, 90).start();
                            new AlertDialog.Builder(mActivity).setTitle("详细信息：").setMessage(sb).setOnDismissListener
                                    (new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    ObjectAnimator.ofFloat(iv_more_info, "rotation", 90, 0).start();
                                    flag = false;
                                }
                            }).create().show();
                            flag = true;
                            flag = true;
                        }
                    }
                });
                FlowLayout flowLayout = holder.getView(R.id.flow_layout);
                flowLayout.clearAllView();
                flowLayout.removeAllViews();
                int space = DensityUtil.dp2px(20, mActivity);
                flowLayout.setSpace(space, space);
                List<String> tags = mBookDetail.getTags();
                if (tags != null) {
                    for (int i = 0; i < tags.size(); i++) {
                        final ColorfulTagView tag = new ColorfulTagView(mActivity);
                        tag.setText(tags.get(i));
                        flowLayout.addView(tag);
                        tag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, ESearchResultActivity.class);
                                intent.putExtra("q", tag.getText());
                                intent.putExtra("type", 1);
                                mActivity.startActivity(intent);
                            }
                        });
                    }
                }
                break;
            case TYPE_BOOK_BRIEF://简介
                ExpandTextView expandTextView = holder.getView(R.id.etv_brief);
                if (mBookDetail.getLongIntro() != null) {
                    expandTextView.setContent(mBookDetail.getLongIntro());
                } else if (mBookDetail.getShortIntro() != null) {
                    expandTextView.setContent(mBookDetail.getShortIntro());
                }
                break;
            case TYPE_BOOK_COMMENT://评论
                List<HotReview.Reviews> reviews = mHotReview.getReviews();
                int pos = position - HEADER_COUNT;
                if (reviews.isEmpty()) {
                    holder.itemView.setVisibility(View.GONE);
                } else if (position == HEADER_COUNT) {
                    //将评论显示
                    holder.getView(R.id.tv_comment_title).setVisibility(View.VISIBLE);
                } else if (position == reviews.size() + 1) {
                    //显示“更多？评论”
                    holder.getView(R.id.tv_more_comment).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_more_comment).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, EBookReviewsActivity.class);
                            intent.putExtra("bookId", mBookDetail.getId());
                            intent.putExtra("bookName", mBookDetail.getTitle());
                            mActivity.startActivity(intent);
                        }
                    });
                }

                Glide.with(mActivity).load(EBookUtils.getImageUrl(reviews.get(pos).getAuthor().getAvatar())).asBitmap
                        ().centerCrop().into((ImageView) holder.getView(R.id.iv_avatar));

                holder.setText(reviews.get(pos).getAuthor().getNickname(), R.id.tv_user_name)//
                        .setText(reviews.get(pos).getContent(), R.id.tv_comment_content)//
                        .setText(reviews.get(pos).getLikeCount() + "", R.id.tv_favorite_num)//
                        .setText(reviews.get(pos).getUpdated().split("T")[0], R.id.tv_update_time);
                AppCompatRatingBar bar = holder.getView(R.id.rating_bat_hots);
                bar.setRating(reviews.get(pos).getRating());
                break;
            case TYPE_BOOK_RECOMMENT:
                List<BookDetail> series = mBooksByTag.getBooks();
                if (series.isEmpty()) {
                    holder.itemView.setVisibility(View.GONE);
                } else {
                    LinearLayout parent = holder.getView(R.id.ll_series_content);
                    EBookSeriesCeiController controller;
                    parent.removeAllViews();
                    for (int i = 0; i < series.size(); i++) {
                        controller = new EBookSeriesCeiController(series.get(i), mActivity);
                        parent.addView(controller.getContentView());
                    }
                }
                break;
            case TYPE_BOOK_LIST:
                int index = position - (mHotReview.getReviews().size() + HEADER_COUNT) - 1;
               final LikedBookList.RecommendBook book;
                book = mLikedBookList.getBookList().get(index);
                if (index == 0) {
                    holder.getView(R.id.tv_booklist_title).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.tv_booklist_title).setVisibility(View.GONE);
                }
                holder.setText(book.getTitle(), R.id.tv_book_title)//
                        .setText(book.getAuthor(), R.id.tv_author)//
                        .setText(book.getInfo(), R.id.tv_book_info)//
                        .setText(book.getDesc(), R.id.tv_book_description);

                Glide.with(mActivity).load(EBookUtils.getImageUrl(book.getCover())).into((ImageView) holder.getView(R
                        .id.iv_book_img));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(mActivity, EBookZoneActivity.class).putExtra(Constant
                                .BOOK_ZONE_ID, book.getId()));
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        int count = HEADER_COUNT;
        if (mHotReview != null) {
            count += mHotReview.getReviews().size();
            if (!mHotReview.getReviews().isEmpty() && mLikedBookList != null) {
                count += mLikedBookList.getBookList().size();
            }
        }
        if (mBooksByTag != null && !mBooksByTag.getBooks().isEmpty()) {
            count += 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BOOK_INFO;
        } else if (position == 1) {
            return TYPE_BOOK_BRIEF;
        } else if (position > 1 && position < (mHotReview == null ? HEADER_COUNT : mHotReview.getReviews().size() +
                HEADER_COUNT)) {
            return TYPE_BOOK_COMMENT;//评论
        } else if (position == (mHotReview == null ? HEADER_COUNT : mHotReview.getReviews().size() + HEADER_COUNT)) {
            return TYPE_BOOK_RECOMMENT;//更多评论
        } else {
            return TYPE_BOOK_LIST;
        }
    }
}
