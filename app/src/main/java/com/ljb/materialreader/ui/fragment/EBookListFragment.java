package com.ljb.materialreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.EBookListAdater;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.bean.event.GenderChangedEvent;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.Rankings;
import com.ljb.materialreader.ui.activity.MainActivity;
import com.ljb.materialreader.ui.presenter.EBookPresenter;
import com.ljb.materialreader.ui.view.EBookListView;
import com.ljb.materialreader.utils.EBookUtils;
import com.ljb.materialreader.utils.ResourceUtils;
import com.ljb.materialreader.utils.SnUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description : EBook列表界面
 */

public class EBookListFragment extends BaseFragment<EBookPresenter> implements EBookListView {

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private String mSex;
    private int mType;
    private String mCategoryId;
    private List<BookDetail> mBookDetails;
    private EBookListAdater mAdater;

    public static EBookListFragment newInstance(int type, String sex) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("sex", sex);
        EBookListFragment fragment = new EBookListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_content;
    }

    @Override
    protected EBookPresenter getPresenter() {
        return new EBookPresenter(this, this);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSex = bundle.getString("sex", "man");
            mType = bundle.getInt("type");
        }

        mCategoryId = EBookUtils.getRankId(mType, mSex);

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        int spanCount = ResourceUtils.getInteger(R.integer.home_span_count);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRvContent.setLayoutManager(layoutManager);

        //设置适配器
        mBookDetails = new ArrayList<>();
        mAdater = new EBookListAdater(mBookDetails, getActivity(), spanCount);
        mRvContent.setAdapter(mAdater);

        //添加动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        mPresenter.getRating(mCategoryId);

    }


    @Override
    protected void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getRating(mCategoryId);
            }
        });
        mAdater.bindFab(((MainActivity) getActivity()).getFab());
    }


    @Override
    public void showProgress() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(GenderChangedEvent changedEvent) {
    mCategoryId = EBookUtils.getRankId(mType,changedEvent.getGender());
        mPresenter.getRating(mCategoryId);
    }

    @Override
    public void showToast(String msg) {
        SnUtils.showShort(mRvContent, msg);
    }

    @Override
    public void onRefresh(Object data) {
        if (data instanceof Rankings) {
            Rankings.RankingBean rankingBean = ((Rankings) data).getRanking();
            mAdater.addNewData(rankingBean.getBooks());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
