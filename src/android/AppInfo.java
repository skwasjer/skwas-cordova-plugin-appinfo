package com.skwas.cordova.appinfo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.device.Device;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

public class AppInfo extends CordovaPlugin {

	private Activity _activity;
	private ApplicationInfo _appInfo;
	
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        
        _activity = cordova.getActivity();        
        _appInfo = _activity.getApplicationInfo();
    } 
	
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if (action.equals("getAppInfo")) {
			cordova.getThreadPool().execute(new Runnable() {
				@Override
				public void run() {
					JSONObject result = new JSONObject();
					try {
						result.put("name", getApplicationName());
						result.put("version", getVersion());
						result.put("compileDate", getBuildDate());
						result.put("isHardwareAccelerated", getIsHardwareAccelerated());
						result.put("isDebuggable", getIsDebuggable());
					//							result.put("xapp_key", XtifyCordovaPlugin.getAppKey(activity.getApplicationContext()));
					} catch(JSONException e) {}
					Log.i("AppInfo", getBuildDate());
					callbackContext.success(result);   
				   }
				});			
			return true;
		}
		return false;
	}
	
	public String getApplicationName() {
		int stringId = _appInfo.labelRes;
		return _activity.getString(stringId);
	}
	
	public String getVersion() {
		String versionName = "";
		try {
			versionName = _activity.getPackageManager()
				.getPackageInfo(_activity.getPackageName(), 0)
				.versionName;
		} catch (NameNotFoundException e) {}
		
		return versionName;
	}
	
	public String getBuildDate() {
		String buildDate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS",
			//"dd-MM-yyyy HH:mm:ss",
			Locale.getDefault()
			).format(getBuildDate(_activity)) + "Z";
		return buildDate.replace(" ", "T");
	}
	
	public Boolean getIsHardwareAccelerated() {
		Boolean isHardwareAccelerated = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			try {
				Method _isHardwareAccelerated = CordovaWebView.class.getMethod("isHardwareAccelerated", (Class<?>[])null);
				isHardwareAccelerated = (Boolean)_isHardwareAccelerated.invoke(webView, (Object[])null);
			} catch (Exception e) {}
		}
		return isHardwareAccelerated;

	}
	
	public boolean getIsDebuggable() {
		return 0 != ( _appInfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE );
	}

	public static long getBuildDate(Context context) {

		ZipFile zf = null;
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(), 0);
			zf = new ZipFile(ai.sourceDir);
			ZipEntry ze = zf.getEntry("classes.dex");
			long time = ze.getTime();
			return time;

		} catch (Exception e) {
		}

		finally {
			try {
				if (zf != null) zf.close();
			} catch (IOException e) {}
		}

		return 0l;
	}

}