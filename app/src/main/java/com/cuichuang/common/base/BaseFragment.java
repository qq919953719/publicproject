package com.cuichuang.common.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cuichuang.common.R;
import com.cuichuang.common.base.view.BaseModel;
import com.cuichuang.common.base.view.BaseView;
import com.cuichuang.common.base.view.LoadingDialog;
import com.cuichuang.common.base.view.MainView;
import com.cuichuang.common.base.view.ProgressDialog;
import com.cuichuang.common.util.KeyBoardUtils;
import com.cuichuang.common.util.ToastUtils;

import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        mPresenter = createPresenter();
        View view = inflater.inflate(getContentId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        isPrePared = true;
        InitView(view);
        ToolBar();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    protected Unbinder unbinder;
    private boolean isFirstLoad = true;         //是否第一次加载
    private boolean isPrePared = true;          //是否准备完毕
    private boolean isVisible = true;           //是否可见
    protected P mPresenter;
    private LoadingDialog loadingDialog;
    private ProgressDialog progressDialog;
    protected abstract P createPresenter();
    protected abstract int getContentId();

    protected abstract void InitView(View view);

    protected abstract void ToolBar();

    protected abstract void InitData();

    private void LazyLoad() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        unbinder.unbind();
    }



    /**
     * 封装toast方法（自行定制实现）
     *
     * @param str
     */
    public void showToast(String str) {
        ToastUtils.getInstance().showToast(getContext(), str);
    }

    public void showLongToast(String str) {
        ToastUtils.getInstance().showToast2(getContext(), str);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    /**
     * 返回所有状态  除去指定的值  可设置所有（根据需求）
     *
     * @param model
     */
    @Override
    public void onErrorCode(BaseModel model) {
        if (model.getCode() .equals("10000000")) {
            //处理些后续逻辑   如果某个页面不想实现  子类重写这个方法  将super去掉  自定义方法
//            App.put();
//            startActivity(LoginActivity.class);
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissMissDialog();
    }

    public void showLoadingDialog() {
        showLoadingDialog("加载中...");
    }

    /**
     * 加载  黑框...
     */
    public void showLoadingDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.setMessage(msg);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 消失  黑框...
     */
    public void dissMissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.getProgressBar().performAnimation();
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.getProgressBar().releaseAnimation();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onProgress(int progress) {
        if (progressDialog != null) {
            progressDialog.updateProgress(progress);
        }
    }




    /**
     * 以下是关于软键盘的处理
     */

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText et = (EditText) v;
            for (int id : ids) {
                if (et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }






}
