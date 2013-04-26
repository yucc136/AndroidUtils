package com.yucc.utils;

import android.util.Log;

import com.uwetrottmann.androidutils.BuildConfig;

public class Logger {
	private static final boolean DEBUG = BuildConfig.DEBUG ;

	public static final void d(String tag, String msg){
		if(DEBUG) Log.d(tag, msg) ;
	}
	public static final void d(Object obj, String msg){
		d(obj.getClass().getSimpleName(), msg) ;
	}
	
	public static final void e(String tag, String msg){
		Log.e(tag, msg) ;
	}
	
	public static final void i(String tag, String msg){
		if(DEBUG) Log.i(tag, msg) ;
	}
	
	public static final void v(String tag, String msg){
		if(DEBUG) Log.v(tag, msg) ;
	}
	
}
