package com.yucc.utils;

import android.util.Log;

public class Logger {
	private static int level = Log.INFO;
	
	public static void setLogLevel(int newLevel) {
		level = newLevel;
	}
	
	private static boolean isLogEnable(int logLevel) {
		return logLevel >= level;
	}
	
	public static final void d(String tag, String msg) {
		if (isLogEnable(Log.DEBUG))
			Log.d(tag, msg);
	}
	
	public static final void d(Object obj, String msg) {
		d(obj.getClass().getSimpleName(), msg);
	}
	
	public static final void d(Object obj, String msg, Object... args) {
		d(obj, String.format(msg, args));
	}
	
	public static final void e(Object obj, String msg) {
		e(obj.getClass().getSimpleName(), msg);
	}
	
	public static final void e(String tag, String msg) {
		if (isLogEnable(Log.ERROR))
			Log.e(tag, msg);
	}
	
	public static final void i(String tag, String msg) {
		if (isLogEnable(Log.INFO))
			Log.i(tag, msg);
	}
	
	public static final void i(Object obj, String msg) {
		i(obj.getClass().getSimpleName(), msg);
	}
	
	public static final void v(String tag, String msg) {
		if (isLogEnable(Log.VERBOSE))
			Log.v(tag, msg);
	}
}
