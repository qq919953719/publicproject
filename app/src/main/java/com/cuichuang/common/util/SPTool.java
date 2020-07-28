package com.cuichuang.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;


import com.cuichuang.common.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SPTool {

	private static final String SP_NAME = BuildConfig.APPLICATION_ID;
	private static SharedPreferences.Editor editor;
	private static SharedPreferences sp;

	private SPTool() {

	}

	private static void init(Context context) {
		if (editor == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			editor = sp.edit();
		}
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 */
	public static int getInt(Context context, String key, int defValue) {
		init(context);
		return sp.getInt(key, defValue);
	}

	public static String getString(Context context, String key, String defValue) {
		init(context);
		return sp.getString(key, defValue);
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		init(context);
		return sp.getBoolean(key, defValue);
	}

	public static long getLong(Context context, String key, long defValue) {
		init(context);
		return sp.getLong(key, defValue);
	}

	public static float getFloat(Context context, String key, float defValue) {
		init(context);
		return sp.getFloat(key, defValue);
	}
	
	public static void putInt(Context context, String key, int value) {
		init(context);
		editor.putInt(key, value);
		editor.commit();
	}

	public static void putString(Context context, String key, String value) {
		init(context);
		editor.putString(key, value);
		editor.commit();
	}

	public static void putBoolean(Context context, String key, boolean value) {
		init(context);
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void putFloat(Context context, String key, float value) {
		init(context);
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void putLong(Context context, String key, long value) {
		init(context);
		editor.putLong(key, value);
		editor.commit();
	}

	public static void clear(Context context) {
		init(context);
		editor.clear();
		editor.commit();
	}

	public static void remove(Context context, String key) {
		init(context);
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 读取已选图片路径
	 */
	public static List<String> getSeletedImages(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		String images = sharedPreferences.getString(key, "");
		List<String> list = new ArrayList<String>();
		if (TextUtils.isEmpty(images)) {
			return list;
		}
		String[] imgArr = images.split(":");
		for (String item : imgArr) {
			if (TextUtils.isEmpty(item)) {
				continue;
			}
			list.add(item);
		}
		return list;
	}

	/**
	 * 读取已选网络图片路径
	 */
	public static List<String> getSeletedNetImages(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		String images = sharedPreferences.getString(key, "");
		List<String> list = new ArrayList<String>();
		if (TextUtils.isEmpty(images)) {
			return list;
		}
		String[] imgArr = images.split(",");
		for (String item : imgArr) {
			if (TextUtils.isEmpty(item)) {
				continue;
			}
			list.add(item);
		}
		return list;
	}

	/**
	 * 保存已选图片路径
	 */
	public static void saveSelectedImags(Context context, String key, List<String> imgList) {
		init(context);
		if (imgList == null) {
			return;
		}
		StringBuffer result = new StringBuffer("");
		for (String item : imgList) {
			result.append(item).append(":");
		}
		editor.putString(key, result.toString());
		editor.commit();
	}

	/**
	 * 保存bean
	 * @param context
	 * @param t
	 * @param keyName
	 * @param <T>
	 */
	public static <T> void saveBean2Sp(Context context, T t, String keyName) {
		init(context);
		ByteArrayOutputStream bos;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(t);
			byte[] bytes = bos.toByteArray();
			String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
			editor.putString(keyName, ObjStr);
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.flush();
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取bean
	 * @param context
	 * @param keyNme
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> T getBeanFromSp(Context context, String keyNme) {
		init(context);
		byte[] bytes = Base64.decode(sp.getString(keyNme, ""), Base64.DEFAULT);
		ByteArrayInputStream bis;
		ObjectInputStream ois = null;
		T obj = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
}
