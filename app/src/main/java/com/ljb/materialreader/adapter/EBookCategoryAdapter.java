package com.ljb.materialreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.ebook.CategoryList;

import java.util.List;

/**
 * Author      :meloon
 * Date        :2018/1/12
 * Description :
 */

public class EBookCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    //标题
    public static final int TYPE_TITLE = 0;
    //男生item
    public static final int TYPE_ITEM_MAN = 1;
    //女生Item
    public static final int TYPE_ITEM_WOMEN = 2;

    private List<CategoryList.CategoryBean> mMans;
    private List<CategoryList.CategoryBean> mWomens;
    private Context mContext;
    private LayoutInflater mInflater;

    public List<CategoryList.CategoryBean> getMans() {
        return mMans;
    }

    public List<CategoryList.CategoryBean> getWomen() {
        return mWomens;
    }

    public EBookCategoryAdapter(List<CategoryList.CategoryBean> mans, List<CategoryList.CategoryBean> women, Context
            context) {
        mMans = mans;
        mWomens = women;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        View view;
        switch (viewType) {
            case TYPE_TITLE:
                view = mInflater.inflate(R.layout.item_ebook_category_title, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_ITEM_MAN:
                view = mInflater.inflate(R.layout.item_ebook_category, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_ITEM_WOMEN:
                view = mInflater.inflate(R.layout.item_ebook_category, parent, false);
                holder = new BaseViewHolder(view);
                break;
            default:
                view = mInflater.inflate(R.layout.item_ebook_category_title, parent, false);
                holder = new BaseViewHolder(view);
                break;
        }
        return holder;
    }

    public void addNewData(List<CategoryList.CategoryBean> mans, List<CategoryList.CategoryBean> womens) {
        mMans.clear();
        mWomens.clear();
        mMans.addAll(mans);
        mWomens.addAll(womens);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_TITLE:
                if (position == 0) {
                    holder.setText("男生", R.id.tv_catory_title);
                } else {
                    holder.setText("女生", R.id.tv_catory_title);
                }
                break;
            case TYPE_ITEM_MAN:
                int pos = position - 1;
                holder.setText(mMans.get(pos).getName(), R.id.tv_ceil_name)//
                        .setText(mMans.get(pos).getBookCount() + "", R.id.tv_ceil_book_count);
                break;
            case TYPE_ITEM_WOMEN:
                int posi = position - 2 - mMans.size();
                holder.setText(mWomens.get(posi).getName(), R.id.tv_ceil_name)//
                        .setText(mWomens.get(posi).getBookCount() + "", R.id.tv_ceil_book_count);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == mMans.size() + 1) {
            return TYPE_TITLE;
        } else if (position >= mMans.size() + 2) {
            return TYPE_ITEM_WOMEN;
        } else {
            return TYPE_ITEM_MAN;
        }
    }

    @Override
    public int getItemCount() {
        return mMans.size() + mWomens.size() + 2;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
