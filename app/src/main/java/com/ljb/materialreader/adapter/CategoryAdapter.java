package com.ljb.materialreader.adapter;

import android.content.Context;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.CategoryBean;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :分类列表的适配器
 */

public class CategoryAdapter extends BaseRvAdapter<CategoryBean> {

    public CategoryAdapter(List<CategoryBean> datas, Context context) {
        super(datas, R.layout.item_category, context);
    }

    @Override
    protected void convert(BaseViewHolder holder, CategoryBean data) {
        holder.setText(data.getName(), R.id.tv_ceil_name)//
                .setImageView(data.getIconId(), R.id.iv_category_icon);
    }
}
