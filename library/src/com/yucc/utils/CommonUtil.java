package com.yucc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;

public class CommonUtil {
	
	public static String formatToday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		return format.format(Calendar.getInstance().getTime());
	}
	
	public static String convertStreamToString(InputStream is, Charset charset)
			throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();
			
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static final SpannableString hightText(String origin, int start,
			int end, int hightColor) {
		SpannableString builder = new SpannableString(origin);
		builder.setSpan(new ForegroundColorSpan(hightColor), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}
	
	public static final void dynamicSetFullScreen(Activity activity) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		activity.getWindow().setAttributes(attrs);
		activity.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
	public static final void dynamicQuitFullScreen(Activity activity) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.getWindow().setAttributes(attrs);
		activity.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	
	public static boolean isJellyBeanOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}
	
	public static boolean isICSOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	public static boolean isHoneycombOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}
	
	/**
	 * Check if ActionBar is available.
	 * 
	 * @return
	 */
	public static boolean hasActionBar() {
		return isHoneycombOrHigher();
	}
	
	public static boolean isGingerbreadOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}
	
	public static boolean isFroyoOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	
	public static boolean isGoogleTV(Context context) {
		return context.getPackageManager().hasSystemFeature(
				"com.google.android.tv");
	}
	
	/**
	 * Checks if {@link Environment}.MEDIA_MOUNTED is returned by
	 * {@code getExternalStorageState()} and therefore external storage is read-
	 * and writeable.
	 * 
	 * @return
	 */
	public static boolean isExtStorageAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
	
	/**
	 * Whether there is any network with a usable connection.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isConnected();
		}
		return false;
	}
	
	/**
	 * Whether the connected connection is mobile network.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileNetworkAvailableOnly(Context context) {
		boolean mobileNetworkAvailable = true;
		boolean wifiNetworkAvailable = true;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		final NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
		for (int i = 0; i < infos.length; i++) {
			final NetworkInfo info = infos[i];
			if (info.getType() == ConnectivityManager.TYPE_MOBILE
					&& !info.isConnected()) {
				mobileNetworkAvailable = false;
			}
			if (info.getType() == ConnectivityManager.TYPE_WIFI
					&& !info.isConnected()) {
				wifiNetworkAvailable = false;
			}
		}
		
		return mobileNetworkAvailable && !wifiNetworkAvailable;
	}
	
	/**
	 * Whether WiFi has an active, usable connection.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo != null) {
			return wifiNetworkInfo.isConnected();
		}
		return false;
	}
	
	/**
	 * Whether 2G has an active, usable connection.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean is2GConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) {
			if (mobileNetworkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
					|| mobileNetworkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
					|| mobileNetworkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Copies the contents of one file to the other using {@link FileChannel}s.
	 * 
	 * @param src
	 *            source {@link File}
	 * @param dst
	 *            destination {@link File}
	 * @throws IOException
	 */
	public static void copyFile(File src, File dst) throws IOException {
		FileInputStream in = new FileInputStream(src);
		FileOutputStream out = new FileOutputStream(dst);
		FileChannel inChannel = in.getChannel();
		FileChannel outChannel = out.getChannel();
		
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
		
		in.close();
		out.close();
	}
	
	/**
	 * Copies data from one input stream to the other using a buffer of 8
	 * kilobyte in size.
	 * 
	 * @param input
	 *            {@link InputStream}
	 * @param output
	 *            {@link OutputStream}
	 * @return
	 * @throws IOException
	 */
	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	
	/**
	 * Execute an {@link AsyncTask} on a thread pool.
	 * 
	 * @param task
	 *            Task to execute.
	 * @param args
	 *            Optional arguments to pass to
	 *            {@link AsyncTask#execute(Object[])}.
	 * @param <T>
	 *            Task argument type.
	 */
	@TargetApi(11)
	public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... args) {
		// TODO figure out how to subclass abstract and generalized AsyncTask,
		// then put this there
		if (isHoneycombOrHigher()) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
		} else {
			task.execute(args);
		}
	}
	
	/**
	 * Returns an {@link InputStream} using {@link HttpURLConnection} to connect
	 * to the given URL.
	 */
	public static InputStream downloadUrl(String urlString) throws IOException {
		HttpURLConnection conn = buildHttpUrlConnection(urlString);
		conn.connect();
		
		InputStream stream = conn.getInputStream();
		return stream;
	}
	
	/**
	 * Returns an {@link HttpURLConnection} using sensible default settings for
	 * mobile and taking care of buggy behavior prior to Froyo.
	 */
	public static HttpURLConnection buildHttpUrlConnection(String urlString)
			throws MalformedURLException, IOException {
		disableConnectionReuseIfNecessary();
		
		URL url = new URL(urlString);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		return conn;
	}
	
	/**
	 * Prior to Android 2.2 (Froyo), {@link HttpURLConnection} had some
	 * frustrating bugs. In particular, calling close() on a readable
	 * InputStream could poison the connection pool. Work around this by
	 * disabling connection pooling. see here for more info:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}
	
	/**
	 * Check if OS version has a http URLConnection bug. See here for more
	 * information:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return
	 */
	public static boolean hasHttpConnectionBug() {
		return !isFroyoOrHigher();
	}
	
	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path
	 *            The path to check
	 * @return The space available in bytes
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}
	
	public static boolean isListEmpty(List<? extends Object> list) {
		return (list == null || list.size() == 0) ? true : false;
	}
	
	/**
	 * Get the memory class of this device (approx. per-app memory limit)
	 * 
	 * @param context
	 * @return
	 */
	public static int getMemoryClass(Context context) {
		return ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	}
	
	/**
	 * Check if external storage is built-in or removable.
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}
	
	/**
	 * Get the external app cache directory.
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			File cacheDir = context.getExternalCacheDir();
			if (cacheDir != null) {
				return cacheDir;
			}
		}
		
		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}
	
	/**
	 * Get a usable cache directory (external if available, internal otherwise).
	 * 
	 * @param context
	 *            The context to use
	 * @param uniqueName
	 *            A unique directory name to append to the cache dir
	 * @return The cache dir
	 */
	public static File getSdcardFileDir(Context context) {
		
		// Check if media is mounted or storage is built-in, if so, try and use
		// external cache dir
		// otherwise use internal cache dir
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) || !isExternalStorageRemovable() ? Environment
				.getExternalStorageDirectory().getPath() : context
				.getCacheDir().getPath();
		
		return new File(cachePath + File.separator + "MyGirls");
	}
	
	/**
	 * Check if OS version has built-in external cache dir method.
	 * 
	 * @return
	 */
	public static boolean hasExternalCacheDir() {
		return isFroyoOrHigher();
	}
	
	public static final boolean isSmallerThanVersionCode(Context context,
			int value) {
		try {
			int versionCode = context.getPackageManager()
			
			.getPackageInfo(context.getPackageName(), 0).versionCode;
			return value < versionCode ? true : false;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查询当前应用清单中的所有activity
	 * 
	 * @param ctx
	 * @return
	 */
	public static ArrayList<String> getActivities(Context ctx) {
		ArrayList<String> result = new ArrayList<String>();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.setPackage(ctx.getPackageName());
		for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(
				intent, 0)) {
			result.add(info.activityInfo.name);
		}
		return result;
	}
	
	/**
	 * 检查是否有匹配Intent的activity
	 * 
	 * @param context
	 * @param action
	 * @return
	 */
	public static boolean isIntentAvailableForActivity(Context context,
			String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	/**
	 * 检查是否有匹配Intent的service
	 * 
	 * @param context
	 * @param action
	 * @return
	 */
	public static boolean isIntentAvailableForServices(Context context,
			Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentServices(intent,
				PackageManager.GET_SERVICES);
		return list.size() > 0;
	}
}
