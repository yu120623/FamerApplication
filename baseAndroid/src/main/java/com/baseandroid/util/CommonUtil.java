package com.baseandroid.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baseandroid.R;

public class CommonUtil {
	private static Toast t;
	private static ProgressDialog simpleDialog;
	private static Integer screenWith;
	private static Integer screenHeight;
	private static long lastClickTime;  
	public static void replaceFragment(FragmentManager manager,Fragment frag,int layoutId){
		manager.beginTransaction().replace(layoutId, frag).commit();
	}
	public static void showMessage(Context context, String message){
		showMessage(context, message, Toast.LENGTH_SHORT);
	}

	public static void showMessage(Context context, int message){
		showMessage(context, context.getResources().getString(message), Toast.LENGTH_SHORT);
	}
	
	public static void showMessage(Context context, int message, int duration){
		showMessage(context, context.getResources().getString(message),duration);
	}
	
	public static void showMessage(Context context, String message, int duration){
		Toast.makeText(context, message, duration).show();
//		if(t==null){
//			t = new Toast(context);			
//		}		
//		View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
//		TextView toastText = (TextView) view.findViewById(R.id.toast_titleText);
//		toastText.setText(message);
//		t.setView(view);
//		t.setGravity(Gravity.BOTTOM, 0, 0);
//		t.setDuration(duration);
//		t.show();
	}
	
	public static int getScreenWith(WindowManager windowManager){
		if(screenWith == null){
			DisplayMetrics dm = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(dm);
			screenWith = dm.widthPixels;
		}
		return screenWith;
	}
	
	public static int getScreenHeight(WindowManager windowManager){
		if(screenHeight == null){
			DisplayMetrics dm = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(dm);
			screenHeight = dm.heightPixels;
		}
		return screenHeight;
	}
	
    
	public static boolean isStringNullOrEmpty(String str){
		if(str == null || str.trim().equals(""))return true;
		return false;
	}

	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	public static byte[] getBitmap2Bytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	
	public static Bitmap getVideoScreenShot(String filePath){
		MediaMetadataRetriever media = new MediaMetadataRetriever();
		media.setDataSource(filePath);
		Bitmap bitmap = media.getFrameAtTime(); 
		media.release();
		return bitmap;
	}
	
	public static byte[] getFile2Bytes(File file){
		byte[] buffer = null;  
		try{   
			FileInputStream fis = new FileInputStream(file);  
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			byte[] b = new byte[1024];  
			int n;  
			while ((n = fis.read(b)) != -1) {  
				bos.write(b, 0, n);  
			}  
			fis.close();  
			bos.close();  
			buffer = bos.toByteArray();  
		}  
		catch (IOException e)  {  
			e.printStackTrace();  
		}  
		return buffer;  
	}
	
	public static void showSimpleProgressDialog(String msg,Activity activity){
		showSimpleProgressDialog(msg, activity,true);
	}
	
	public static void showSimpleProgressDialog(String msg,Activity activity,boolean isCancel){
		simpleDialog = new ProgressDialog(activity);
		simpleDialog.setMessage(msg);
		simpleDialog.setCancelable(isCancel);
		simpleDialog.setCanceledOnTouchOutside(isCancel);
		simpleDialog.show();
	}
	
	public static void dismissSimpleProgressDialog(){
		if(simpleDialog != null)
			simpleDialog.dismiss();
	}
	
	public static int Px2Dp(Context context, float px) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (px / scale + 0.5f); 
	} 
	
	public static int Dp2Px(Context context, float dp) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	} 
	
	public static int Sp2Px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  

	
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();  
        long timeD = time - lastClickTime;  
        if ( 0 < timeD && timeD < 1000) {     
            return true;     
        }     
        lastClickTime = time;     
        return false;     
    }  
    
    public static float getVersion(Context context){
    	PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}  
		String versionName = info.versionName;
		if(info == null)
			return 0;
		return Double.valueOf(versionName).floatValue();
    }
    
    
    //安装文件
    public static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }
    
    public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}
    
    public static void hideKeyBoard(Activity activity){
    	//if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
    		if (activity.getCurrentFocus() != null){
    			InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
    		}
    	//}
    }
    
    public static void gotoCropImg(Uri uri,Activity activity){
    	Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");  
          
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale",true);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent,3);
    }
    public static void gotoCropImg(Uri uri,android.app.Fragment activity){
    	Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");  
          
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale",true);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent,3);
    }
    
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    
    public static File getUploadFolder(){
    	File file = Environment.getExternalStorageDirectory();
    	File path = new File(file,File.separator+"sx"+"/up/");
    	if(!path.exists())
    		path.mkdir();
    	return path;
    }
}

