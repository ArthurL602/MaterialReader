package com.ljb.materialreader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.DiscoverAdapter;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.ui.activity.CaptureActivity;
import com.ljb.materialreader.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :发现Fragment
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private int type = 0;//0 :douban; 1:ebook
    private List<String> mDatas;
    private LinearLayoutManager mLayoutManager;
    private DiscoverAdapter mAdapter;

    public static DiscoverFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        type = getArguments().getInt("type");
        initData();

        mSwipeRefresh.setEnabled(false);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(mLayoutManager);

        //设置适配器
        mAdapter = new DiscoverAdapter(mDatas, getActivity());
        mRv.setAdapter(mAdapter);
        //添加动画
        mRv.setItemAnimator(new DefaultItemAnimator());

    }

    private void initData() {
        mDatas = new ArrayList<>();
        if (type == 0) {
            mDatas.add("小说");
            mDatas.add("日本");
            mDatas.add("历史");
            mDatas.add("外国文学");
            mDatas.add("文学");
            mDatas.add("漫画");
            mDatas.add("中国");
            mDatas.add("心理学");
            mDatas.add("随笔");
            mDatas.add("哲学");
            mDatas.add("中国文学");
            mDatas.add("绘本");
            mDatas.add("推理");
            mDatas.add("美国");
            mDatas.add("爱情");
            mDatas.add("经典");
            mDatas.add("日本文学");
            mDatas.add("传记");
            mDatas.add("文化");
            mDatas.add("散文");
            mDatas.add("青春");
            mDatas.add("社会学");
            mDatas.add("旅行");
            mDatas.add("英国");
            mDatas.add("科普");
            mDatas.add("东野圭吾");
            mDatas.add("科幻");
            mDatas.add("言情");
            mDatas.add("生活");
            mDatas.add("艺术");
            mDatas.add("成长");
            mDatas.add("村上春树");
            mDatas.add("悬疑");
            mDatas.add("经济学");
            mDatas.add("台湾");
            mDatas.add("设计");
            mDatas.add("管理");
            mDatas.add("励志");
            mDatas.add("法国");
            mDatas.add("思维");
            mDatas.add("社会");
            mDatas.add("心理");
            mDatas.add("政治");
            mDatas.add("武侠");
            mDatas.add("经济");
            mDatas.add("奇幻");
            mDatas.add("诗歌");
            mDatas.add("童话");
            mDatas.add("摄影");
            mDatas.add("日本漫画");
            mDatas.add("韩寒");
            mDatas.add("商业");
            mDatas.add("建筑");
            mDatas.add("女性");
            mDatas.add("金融");
            mDatas.add("耽美");
            mDatas.add("亦舒");
            mDatas.add("人生");
            mDatas.add("宗教");
            mDatas.add("电影");
            mDatas.add("互联网");
            mDatas.add("英国文学");
            mDatas.add("推理小说");
            mDatas.add("王小波");
            mDatas.add("计算机");
            mDatas.add("杂文");
            mDatas.add("古典文学");
            mDatas.add("儿童文学");
            mDatas.add("美国文学");
            mDatas.add("三毛");
            mDatas.add("数学");
            mDatas.add("投资");
            mDatas.add("网络小说");
            mDatas.add("政治学");
            mDatas.add("名著");
            mDatas.add("职场");
            mDatas.add("余华");
            mDatas.add("张爱玲");
            mDatas.add("好书，值得一读");
            mDatas.add("香港");
            mDatas.add("美食");
            mDatas.add("安妮宝贝");
            mDatas.add("教育");
            mDatas.add("我想读这本书");
            mDatas.add("个人管理");
            mDatas.add("人类学");
            mDatas.add("郭敬明");
            mDatas.add("回忆录");
            mDatas.add("工具书");
            mDatas.add("穿越");
            mDatas.add("德国");
            mDatas.add("東野圭吾");
            mDatas.add("纪实");
            mDatas.add("金庸");
            mDatas.add("中国历史");
            mDatas.add("人性");
            mDatas.add("游记");
            mDatas.add("编程");
            mDatas.add("轻小说");
            mDatas.add("思想");
            mDatas.add("营销");
            mDatas.add("阿加莎·克里斯蒂");
            mDatas.add("教材");
            mDatas.add("英语");
            mDatas.add("国学");
            mDatas.add("时间管理");
            mDatas.add("散文随笔");
            mDatas.add("心灵");
            mDatas.add("当代文学");
            mDatas.add("日系推理");
            mDatas.add("灵修");
            mDatas.add("法国文学");
            mDatas.add("几米");
            mDatas.add("治愈");
            mDatas.add("政治哲学");
            mDatas.add("BL");
            mDatas.add("科学");
            mDatas.add("科幻小说");
            mDatas.add("音乐");
            mDatas.add("人文");
        }
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new DiscoverAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof ImageView) {
                    startActivity(new Intent((MainActivity) getActivity(), CaptureActivity.class));
                } else {
                    ((MainActivity) getActivity()).showSearchView();
                }

            }
        });
    }


}
