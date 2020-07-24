package com.cuichuang.common;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.cuichuang.common.base.BaseActivity;
import com.cuichuang.common.fragment.FourFragment;
import com.cuichuang.common.fragment.OneFragment;
import com.cuichuang.common.fragment.ThreeFragment;
import com.cuichuang.common.fragment.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


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
    private TextView mTextViewList[];
    private String strTitleList[];
    private Fragment mFragmentList[];
    FragmentManager fm;

    private void addFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            fm.beginTransaction().show(fragment).commit();
        } else {
            fm.beginTransaction().add(R.id.lv_main_content, fragment).commit();
        }
    }

    @Override
    protected void initView() {

        InitData();
        selectButton(0);
    }

    void InitData() {
        fm = getSupportFragmentManager();
        mTextViewList = new TextView[]{tvTb1, tvTb2, tvTb3, tvTb4};
        strTitleList = new String[]{"table1", "table2", "table3", "table4"};
        OneFragment mOneFragment = new OneFragment();
        TwoFragment mTwoFragment = new TwoFragment();
        ThreeFragment mThreeFragment = new ThreeFragment();
        FourFragment mFourFragment = new FourFragment();
        mFragmentList = new Fragment[]{mOneFragment, mTwoFragment, mThreeFragment, mFourFragment};
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
                mTextViewList[i].setText("选中" + strTitleList[i]);
                addFragment(mFragmentList[position]);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
