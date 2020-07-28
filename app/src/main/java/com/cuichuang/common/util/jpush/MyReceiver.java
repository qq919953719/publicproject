package com.cuichuang.common.util.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.cuichuang.common.util.Consts;
import com.cuichuang.common.util.EnterCheckUtil;
import com.cuichuang.common.util.ULog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                ULog.d(Consts.TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                ULog.d(Consts.TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                //showCustomeMsg(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                ULog.d(Consts.TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                ULog.d(Consts.TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                ULog.d(Consts.TAG, "[MyReceiver] 用户点击打开了通知");
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                ULog.e(Consts.TAG, extras);
                openNotification(context, bundle);
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                ULog.d(Consts.TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                ULog.w(Consts.TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                ULog.d(Consts.TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    /**
     * 处理通知消息
     *
     * @param context
     * @param bundle
     */
    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (TextUtils.isEmpty(extras)) {
            return;
        }
        String content = TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_ALERT)) ? "" : bundle.getString(JPushInterface.EXTRA_ALERT);
        String title = TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)) ? "" : bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        try {
            JSONObject json = new JSONObject(extras);
            int type = json.optInt("push_type");
            String tid = json.optString("push_id");
            String url = json.optString("push_url");
            ULog.e(Consts.TAG, type + " " + tid + " " + url);
            if (!EnterCheckUtil.getInstance().isLogin(false)) {
              //  Intent intent = new Intent(context, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                return;
            }
            switch (type) { //1、组织生活 2、书记述职 3、材料上报 4、其它 5.活动报名 6.培训班报名 7.资料共享
                case 1:
                   // OrganizationLifedetailActivity.goTo(context, tid);
                    break;
                case 2:
                   // SecretReportContentActivity.goTo(context, tid);
                    break;
                case 3:
                   // SpecialMeterialReportDetailActivity.goTo(context, tid);
                    break;
                case 4:
                    //NotiNewsDetailActivity.goTo(context, tid);
                    break;
                case 5:
                   // ActsEncrollDetailActivity.goTo(context,tid);
                    break;
                case 6:
                   // TrainCourseDetailActivity.goTo(context,tid);
                    break;
                case 7:
                    //DataShareingDetailActivity.goTo(context,tid);
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                   // NewsDetailActivity.goTo(context,tid);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ULog.e(Consts.TAG, "极光推送解析错误");
        }
    }

    /**
     * 处理自定义消息
     */
//	private static int notiId = 0;
//	private void showCustomeMsg(Context context,Bundle bundle){
//		String title = TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_TITLE))?"":bundle.getString(JPushInterface.EXTRA_TITLE);
//		String content = TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_MESSAGE))?"":bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		if (TextUtils.isEmpty(extras)) {
//			return;
//		}
//		Intent mIntent = null;
//		try {
//			JSONObject json = new JSONObject(extras);
//			String pushType=json.optString("push_type");
//			String pushId=json.optString("push_id");
//			String pushUrl=json.optString("push_url");
//			ULog.e(Consts.TAG, pushType+" "+pushId+" "+pushUrl);
//			mIntent = new Intent(context, VideoPlayerActivity.class);
//			mIntent.putExtra("termId", pushId);
//			mIntent.putExtra("description", content);
//			mIntent.putExtra("liveStreaming", true);
//			mIntent.putExtra("cache", false);
//			if(TextUtils.equals(pushType,"0")){  //图文直播
//				mIntent.putExtra("videoPath", "");
//				mIntent.putExtra("thumb", pushUrl);
//				mIntent.putExtra("isPicLive",true);
//			}else{								//视频直播
//				mIntent.putExtra("videoPath", pushUrl);
//				mIntent.putExtra("thumb", "");
//				mIntent.putExtra("isPicLive",false);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//			ULog.e(Consts.TAG, "极光推送解析错误");
//		}
//		NotificationManager  notificationManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
//		nBuilder.setSmallIcon(R.drawable.jpush_notification_icon)
//				//.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
//				.setTicker(title)
//				.setContentTitle(title)
//				.setContentText(content)
//				.setWhen(System.currentTimeMillis())
//				.setContentIntent(pendingIntent)
//				.setLights(0xFF0000, 3000, 3000)
//				.setAutoCancel(true)
//				.setColor(Color.parseColor("#ff7000"));
//		notificationManager.notify(notiId++,nBuilder.build());
//	}
}
