package com.cuichuang.common.base;

import android.app.Activity;
import android.app.Application;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.cuichuang.common.BuildConfig;
import com.cuichuang.common.R;
import com.cuichuang.common.util.Consts;
import com.cuichuang.common.util.ULog;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class myApplication extends Application {
    public List<Activity> mActivityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
//WbSdk.install(this,new AuthInfo(this, Consts.SinaAPP_KEY, Consts.SINAREDIRECT_URL, Consts.SCOPE));
        //禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.openActivityDurationTrack(false);
       // OkHttpUtil.getInstance().init(getApplicationContext());

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                ULog.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
//        LeakCanary.install(this);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
        //极光推送
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        //bugtags
        //在这里初始化

        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置，默认 true
                trackingCrashLog(true).//是否收集crash，默认 true
                trackingConsoleLog(true).//是否收集console log，默认 true
                trackingUserSteps(true).//是否收集用户操作步骤，默认 true
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
                build();

        // Bugtags.start("3b99fec29709ada7c1f7ce4bd8682688", this, Bugtags.BTGInvocationEventBubble, options);
        Bugtags.start(getResources().getString(R.string.bugtags_appkey), this, Bugtags.BTGInvocationEventNone, options);
        Consts.BASE_DOMAIN = getResources().getString(R.string.app_base_domain);
        Consts.BASE_URL = Consts.BASE_DOMAIN + "index.php/app";
        //EnterCheckUtil.getInstance().init(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    public void removeAllActivity() {
        if (mActivityList.size() == 0) {
            return;
        }

        for (int i = 0; i <= mActivityList.size(); i++) {

            Activity mActivity = mActivityList.get(i);
            mActivity.finish();

        }
    }


    public void removeActivity(String strActivityName) {

        if (mActivityList.size() == 0) {
            return;
        }

        for (int i = 0; i <= mActivityList.size(); i++) {

            Activity mActivity = mActivityList.get(i);
            if (mActivity.getComponentName().getClassName().equals(strActivityName)) {
                mActivityList.remove(i);
                mActivity.finish();
            }


        }
    }
}
