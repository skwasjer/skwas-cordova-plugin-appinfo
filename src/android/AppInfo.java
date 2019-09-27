package com.skwas.cordova.appinfo;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import [app_id].BuildConfig;

public class AppInfo extends CordovaPlugin {

	private Activity _activity;
	private ApplicationInfo _appInfo;

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);

		_activity = cordova.getActivity();
		_appInfo = _activity.getApplicationInfo();
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		if (action.equals("getAppInfo")) {
			cordova.getThreadPool().execute(new Runnable() {
				@Override
				public void run() {
					JSONObject result = new JSONObject();
					try {
						result.put("name", _activity.getString(_appInfo.labelRes));
						result.put("version", BuildConfig.VERSION_NAME);
						result.put("build", BuildConfig.VERSION_CODE);
						result.put("identifier", BuildConfig.APPLICATION_ID);
						result.put("compileDate", BuildConfig.BUILD_TIME);
						result.put("isHardwareAccelerated", getIsHardwareAccelerated());
						result.put("isDebuggable", BuildConfig.DEBUG);
					} catch(JSONException e) {}
					callbackContext.success(result);
				}
			});
			return true;
		}
		return false;
	}

	public Boolean getIsHardwareAccelerated() {
		Boolean isHardwareAccelerated = false;
		try {
			isHardwareAccelerated = webView.getView().isHardwareAccelerated();
		}
		catch (Exception e) {}

		if (!isHardwareAccelerated && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			try {
				Method _isHardwareAccelerated = CordovaWebView.class.getMethod("isHardwareAccelerated", (Class<?>[]) null);
				isHardwareAccelerated = (Boolean) _isHardwareAccelerated.invoke(webView, (Object[]) null);
			} catch (Exception e) {}
		}

		return isHardwareAccelerated;
	}
}
