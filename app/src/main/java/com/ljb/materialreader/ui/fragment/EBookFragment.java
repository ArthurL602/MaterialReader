package com.ljb.materialreader.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.VpAdater;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.event.GenderChangedEvent;
import com.ljb.materialreader.constant.Constant;
import com.ljb.materialreader.ui.activity.MainActivity;
import com.ljb.materialreader.utils.EBookUtils;
import com.ljb.materialreader.utils.ResourceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description : EBook界面
 */

public class EBookFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    //性别
    private String sex = "";
    private List<BaseFragment> mFragments;

    public static EBookFragment newInstance() {
        EBookFragment fragment = new EBookFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("EBook");
        ((MainActivity) getActivity()).setToolbar(mToolbar);
        ((MainActivity) getActivity()).setFab(mFab);

        //获取保存的性别类型
        sex = EBookUtils.getSex();
        initFragment();
        String[] titles = ResourceUtils.getStringArray(R.array.ebook_tab_type);
        VpAdater adater = new VpAdater(getChildFragmentManager(), mFragments, getContext(), titles);
        mVp.setAdapter(adater);
        mVp.setOffscreenPageLimit(5);
        mVp.setCurrentItem(0);
        mTab.setupWithViewPager(mVp);
        mTab.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
    }


    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(EBookListFragment.newInstance(Constant.TYPE_HOT_RANKING, sex));
        mFragments.add(EBookListFragment.newInstance(Constant.TYPE_RETAINED_RANKING, sex));
        mFragments.add(EBookListFragment.newInstance(Constant.TYPE_FINISHED_RANKING, sex));
        mFragments.add(EBookListFragment.newInstance(Constant.TYPE_POTENTIAL_RANKING, sex));
        mFragments.add(EBookCategoryFragment.newInstance());
        mFragments.add(DiscoverFragment.newInstance(1));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.getItem(0);
        if (sex.equals("man")) {
            menuItem.setIcon(R.drawable.ic_action_gender_male);
        } else {
            menuItem.setIcon(R.drawable.ic_action_gender_female);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sex:
                if (sex.equals("man")) {
                    sex = "women";
                    item.setIcon(R.drawable.ic_action_gender_female);
                } else {
                    sex = "man";
                    item.setIcon(R.drawable.ic_action_gender_male);
                }
                break;
        }
        EventBus.getDefault().post(new GenderChangedEvent(sex));
        EBookUtils.putSex(sex);
        return super.onOptionsItemSelected(item);
    }


}
