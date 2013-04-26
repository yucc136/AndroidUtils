package com.yucc.utils;

import android.content.Context;
import android.content.res.Resources;

public class DipPxUtil {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param id
	 *            resId
	 */
	public static int dip2px(int dimenResId, Context context) {
		Resources resources = context.getResources();
		final float scale = resources.getDisplayMetrics().density;
		float dimen = resources.getDimension(dimenResId);
		return (int) (dimen * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param id
	 *            resId
	 */
	public static int px2dip(int dimenResId, Context context) {
		Resources resources = context.getResources();
		final float scale = resources.getDisplayMetrics().density;
		float dimen = resources.getDimension(dimenResId);
		return (int) (dimen / scale + 0.5f);
	}
}
