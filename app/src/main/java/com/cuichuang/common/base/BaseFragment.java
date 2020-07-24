package com.cuichuang.common.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuichuang.common.R;

import java.util.zip.Inflater;


public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        View view = inflater.inflate(getContentId(), container, false);
        isPrePared = true;
        InitView(view);
        ToolBar();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private boolean isFirstLoad = true;         //是否第一次加载
    private boolean isPrePared = true;          //是否准备完毕
    private boolean isVisible = true;           //是否可见

    protected abstract int getContentId();

    protected abstract void InitView(View view);

    protected abstract void ToolBar();

    protected abstract void InitData();

    protected void LazyLoad() {
        if (isFirstLoad && isPrePared && isVisible) {
            InitData();
        }
        isFirstLoad = false;

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            LazyLoad();
        } else {
            isVisible = false;
        }
    }
}
