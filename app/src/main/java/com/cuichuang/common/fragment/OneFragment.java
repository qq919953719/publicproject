package com.cuichuang.common.fragment;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cuichuang.common.R;
import com.cuichuang.common.api.ApiRetrofit;
import com.cuichuang.common.base.BaseFragment;
import com.cuichuang.common.base.view.BaseModel;
import com.cuichuang.common.base.view.MainBean;
import com.cuichuang.common.base.view.MainView;
import com.cuichuang.common.bean.LoginBean;
import com.cuichuang.common.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment<MainPresenter> implements MainView {


    @BindView(R.id.tv_getLoginInfo)
    TextView tvGetLoginInfo;
    @BindView(R.id.tv_setLoginInfo)
    TextView tvSetLoginInfo;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void InitView(View view) {
        tvGetLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getLoginInfo();
            }
        });
    }

    @Override
    protected void ToolBar() {

    }

    @Override
    protected void InitData() {
        Log.e("fragmentone", "懒加载1");

    }


    @OnClick(R.id.tv_getLoginInfo)
    public void onViewClicked() {


    }


    @Override
    public void onLoginSuccess(BaseModel<LoginBean> mLoginBean) {

        tvSetLoginInfo.setText(mLoginBean.getData().getRealname());
    }
}
