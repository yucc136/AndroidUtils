package com.yucc.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class VersionApiUtil {
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static final void removeOnGlobalLayoutListener(View view,OnGlobalLayoutListener listener) {
		if(CommonUtil.isJellyBeanOrHigher()){
			view.getViewTreeObserver()
			.removeOnGlobalLayoutListener(listener);
		}else{
			view.getViewTreeObserver()
			.removeGlobalOnLayoutListener(listener);
		}
	}
}
