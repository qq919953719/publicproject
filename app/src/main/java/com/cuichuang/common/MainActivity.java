package com.cuichuang.common;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.cuichuang.common.base.BaseActivity;
import com.cuichuang.common.base.mainfragmentadapter.MainFragmentPagerAdapter;
import com.cuichuang.common.base.view.BaseModel;
import com.cuichuang.common.base.view.MainView;
import com.cuichuang.common.bean.LoginBean;
import com.cuichuang.common.fragment.FourFragment;
import com.cuichuang.common.fragment.OneFragment;
import com.cuichuang.common.fragment.ThreeFragment;
import com.cuichuang.common.fragment.TwoFragment;
import com.cuichuang.common.presenter.MainPresenter;
import com.cuichuang.common.widget.NoScrollViewPager;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements ViewPager.OnPageChangeListener, MainView {
    @BindView(R.id.lv_main_content)
    LinearLayout lvMainContent;
    @BindView(R.id.tv_tb1)
    TextView tvTb1;
    @BindView(R.id.tv_tb2)
    TextView tvTb2;
    @BindView(R.id.tv_tb3)
    TextView tvTb3;
    @BindView(R.id.tv_tb4)
    TextView tvTb4;
    @BindView(R.id.lv_bottom_table)
    LinearLayout lvBottomTable;
    @BindView(R.id.tv_title)
    TextView tvThemTitle;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.msg_dot)
    TextView msgDot;
    @BindView(R.id.me_panel)
    FrameLayout mePanel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    private TextView mTextViewList[];
    private String strTitleList[];

    private List<Fragment> mFragmentList;
    FragmentManager fm;
    MainFragmentPagerAdapter adapter;

//    private void addFragment(Fragment fragment) {
//        if (fragment.isAdded()) {
//            fm.beginTransaction().show(fragment).commit();
//        } else {
//            fm.beginTransaction().add(R.id.lv_main_content, fragment).commit();
//        }
//    }

    @Override
    protected void initView() {
        createPresenter();
        InitData();
        selectButton(0);
    }

    void InitData() {
        mFragmentList = new ArrayList<>();
        fm = getSupportFragmentManager();
        mTextViewList = new TextView[]{tvTb1, tvTb2, tvTb3, tvTb4};
        strTitleList = new String[]{"table1", "table2", "table3", "table4"};
        OneFragment mOneFragment = new OneFragment();
        TwoFragment mTwoFragment = new TwoFragment();
        ThreeFragment mThreeFragment = new ThreeFragment();
        FourFragment mFourFragment = new FourFragment();
        mFragmentList.add(mOneFragment);
        mFragmentList.add(mTwoFragment);
        mFragmentList.add(mThreeFragment);
        mFragmentList.add(mFourFragment);
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(4);
        viewpager.addOnPageChangeListener(this);
        //设置为false允许左右滑动
        viewpager.setNoScroll(true);
        viewpager.setCurrentItem(0);
    }

    @Override
    protected void initToolBar() {
        tvThemTitle.setText("首页标题dfsfsdfs");
    }

    @Override
    protected int getLayoutContent() {
        return R.layout.activity_main;
    }

    private void selectButton(int position) {
        for (int i = 0; i < mTextViewList.length; i++) {
            if (i == position) {
                mTextViewList[i].setTextColor(Color.parseColor("#FF3030"));
                mTextViewList[i].setText(strTitleList[i]);
                viewpager.setCurrentItem(position);
                tvThemTitle.setText("table" + i);
            } else {
                mTextViewList[i].setTextColor(Color.parseColor("#080808"));
                mTextViewList[i].setText(strTitleList[i]);
            }

        }


    }


    @OnClick({R.id.tv_tb1, R.id.tv_tb2, R.id.tv_tb3, R.id.tv_tb4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tb1:
                selectButton(0);
                break;
            case R.id.tv_tb2:
                selectButton(1);
                break;
            case R.id.tv_tb3:
                selectButton(2);
                break;
            case R.id.tv_tb4:
                selectButton(3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectButton(position);
        if (position == 0) {
            ivScan.setVisibility(View.VISIBLE);
            ivMsg.setVisibility(View.VISIBLE);
            ivMsg.setImageResource(R.drawable.home_top_message);
            ivScan.setImageResource(R.drawable.home_top_scan);
        } if (position == 3) {
            ivScan.setVisibility(View.GONE);
            ivMsg.setImageResource(R.drawable.ic_set);
            ivMsg.setVisibility(View.VISIBLE);
        }else {
            ivScan.setVisibility(View.GONE);
            ivMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onLoginSuccess(BaseModel<LoginBean> o) {

    }
}
