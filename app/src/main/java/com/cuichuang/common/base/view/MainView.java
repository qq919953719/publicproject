package com.cuichuang.common.base.view;




import com.cuichuang.common.bean.LoginBean;
import com.cuichuang.common.bean.TextBean;

import java.util.List;

/**
 * File descripition:
 *
 * @author CC
 * @date 2018/6/19
 */

public interface MainView extends BaseView {

    void onLoginSuccess(BaseModel<LoginBean> mLoginBean);


}
