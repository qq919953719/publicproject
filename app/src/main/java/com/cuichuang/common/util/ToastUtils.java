package com.cuichuang.common.util;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


/**
 *Toast类
 * 
 */
public class ToastUtils {

	private static ToastUtils toastUtils = null;
	private Toast toast = null;
	private TextView toastMsg;

	private ToastUtils() {

	}

	public static ToastUtils getInstance() {
		if (toastUtils == null) {
			synchronized (ToastUtils.class) {
				if (toastUtils == null) {
					toastUtils = new ToastUtils();
				}
			}
		}
		return toastUtils;
	}

	public void showToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public void showToast2(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_LONG);
		}
		toast.show();
	}

//	public void showToastLong(Context context, String msg) {
//		if (toast == null || toast.getView() == null) {
//			toast = new Toast(context);
//			View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
//			toastMsg = (TextView) view.findViewById(R.id.toastMsg);
//			toastMsg.setText(msg);
//			toast.setView(view);
//			toast.setDuration(Toast.LENGTH_LONG);
//		} else {
//			toastMsg.setText(msg);
//			toast.setDuration(Toast.LENGTH_LONG);
//		}
//		toast.show();
//	}

//	public void showToastShort(Context context, String msg) {
//		if (toast == null || toast.getView() == null) {
//			toast = new Toast(context);
//			View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
//			toastMsg = (TextView) view.findViewById(R.id.toastMsg);
//			toastMsg.setText(msg);
//			toast.setView(view);
//			toast.setDuration(Toast.LENGTH_SHORT);
//		} else {
//			toastMsg.setText(msg);
//			toast.setDuration(Toast.LENGTH_SHORT);
//		}
//		toast.show();
//	}

}
