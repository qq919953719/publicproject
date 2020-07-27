package com.cuichuang.common.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuichuang.common.R;
import com.cuichuang.common.base.BaseFragment;
import com.cuichuang.common.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends BaseFragment  {


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void InitView(View view) {

    }

    @Override
    protected void ToolBar() {

    }

    @Override
    protected void InitData() {
        Log.e("fragmentone","懒加载3");
    }

}
