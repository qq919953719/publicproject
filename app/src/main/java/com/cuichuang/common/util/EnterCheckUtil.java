package com.cuichuang.common.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cuichuang.common.base.myApplication;
import com.cuichuang.common.bean.LoginBean;
import com.cuichuang.common.util.jpush.TagAliasOperatorHelper;

import static com.cuichuang.common.util.jpush.TagAliasOperatorHelper.sequence;


/**
 * Created by CC on 2018/5/14.
 * Email:919953719@qq.com
 * 登录、角色判断
 */

public class EnterCheckUtil {
    public static EnterCheckUtil instance;
    private Context context;

    public static EnterCheckUtil getInstance() {
        if (instance == null) {
            instance = new EnterCheckUtil();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param app
     */
    public void init(Application app) {
        context = app.getApplicationContext();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin(boolean isNeedToLogin) {
        LoginBean loginBean = SPTool.getBeanFromSp(context, Consts.SP_LOGIN_BEAN);
        myApplication app = (myApplication) context;
        if (loginBean != null) {
            if (loginBean != null) {
                app.setmLoginBean(loginBean);
                if (!SPTool.getBoolean(context, Consts.SP_JPUSH_ALIAS_REGISTED, false)) {
                    saveLogin(loginBean);
                    ULog.e("设置别名");
                }
                return true;
            }
        }
        if (isNeedToLogin) {
            Intent intent = new Intent();
            intent.setAction("com.xugongyuan.dangjianlite.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return false;
    }

    /**
     * 获取uid和token
     *
     * @return
     */
    public String[] getUid_Token() {
        String uid = "";
        String token = "";
        myApplication app = (myApplication) context;
        if (app.getmLoginBean() != null) {
            if (app.getmLoginBean() != null) {
                uid = app.getmLoginBean().getUid();
                token = app.getmLoginBean().getToken();
            }
        }
        return new String[]{uid, token};
    }

    /**
     * @return 1表示是云嘉；0表示不是云嘉
     */
    public String getIs_lh() {
        String is_lh = "";
        myApplication app = (myApplication) context;
        if (app.getmLoginBean() != null) {
            if (app.getmLoginBean() != null) {
                is_lh = app.getmLoginBean().getIs_lh();
            }
        }
        return is_lh;
    }

    /**
     * 保持登录状态
     *
     * @param loginBean
     */
    public void saveLogin(LoginBean loginBean) {
        SPTool.remove(context, Consts.SP_LOGIN_BEAN);
        SPTool.saveBean2Sp(context, loginBean, Consts.SP_LOGIN_BEAN);
        myApplication app = (myApplication) context;
        app.setLoginReturn(false);
        app.setmLoginBean(loginBean);
        //设置别名
        if (!TextUtils.isEmpty(loginBean.getUid())) {
            ULog.e("cc--", "设置别名成功");
            sequence++;
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.alias = loginBean.getUid();
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
            tagAliasBean.isAliasAction = true;
            TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
            SPTool.putBoolean(context, Consts.SP_JPUSH_ALIAS_REGISTED, true);
        }
    }

    /**
     * 退出登录
     */
    public void outLogin() {
        SPTool.remove(context, Consts.SP_LOGIN_BEAN);
        myApplication app = (myApplication) context;
        app.setmLoginBean(null);
        //删除别名
        sequence++;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.alias = null;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
        SPTool.remove(context, Consts.SP_JPUSH_ALIAS_REGISTED);
    }


    private boolean checkType() {
        boolean isLogin = isLogin(false);
        if (!isLogin) {
            Intent intent = new Intent();
            intent.setAction("com.cuichuang.common.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
        myApplication app = (myApplication) context;
        int roleId = app.getmLoginBean().getRoleid();
        if (roleId == 0) {
            return true;
        }

//        else if (roleId == 2) {
//            return true;
//        }

        else {
            return false;
        }
    }

    /**
     * 是否党员
     *
     * @return
     *///0普通党员  1支部书记  2总支书记   3组织部
    public boolean IS_MEMBER() {
        return checkType();
    }

}
