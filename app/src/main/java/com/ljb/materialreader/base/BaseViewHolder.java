package com.ljb.materialreader.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljb.materialreader.callback.IImageLoader;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : RecyclerView的ViewHolder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(String content, int viewId) {
        TextView textView = getView(viewId);
        textView.setText(content);
        return this;
    }

    public BaseViewHolder setImageView(int resId, int viewId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public BaseViewHolder setImageView(Bitmap bitmap, int viewId) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setImageView(Drawable drawable, int viewId) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageView(String path, int viewId, IImageLoader loader) {
        ImageView iv = getView(viewId);
        loader.loadImage(iv, path);
        return this;
    }
    public void setVisible(int viewId, boolean visible) {
        View  view=getView(viewId);
        view.setVisibility(visible? View.VISIBLE :View.INVISIBLE);
    }
}
