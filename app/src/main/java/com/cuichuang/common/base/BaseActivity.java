package com.cuichuang.common.base;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication application = (myApplication) getApplication();
        application.mActivityList.add(this);
        setContentView(getLayoutContent());
        unbinder = ButterKnife.bind(this);
        initToolBar();
        initView();
    }

    protected abstract void initView();

    protected abstract void initToolBar();

    protected abstract int getLayoutContent();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
