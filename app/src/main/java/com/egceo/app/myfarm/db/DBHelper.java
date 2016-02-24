package com.egceo.app.myfarm.db;

import android.content.Context;

public class DBHelper {
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	
	private static DaoMaster userDaoMaster;
	private static DaoSession userDaoSession;

	public static synchronized DaoMaster getDaoMaster(Context context,String dbName) {
		if (daoMaster == null) {
			DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,dbName, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	public static synchronized DaoSession getDaoSession(Context context,String dbName) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context,dbName);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
	
	
	public static synchronized DaoMaster getUserDaoMaster(Context context,String dbName) {
		if (userDaoMaster == null) {
			DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,dbName, null);
			userDaoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return userDaoMaster;
	}

	public static synchronized DaoSession getUserDaoSession(Context context,String dbName) {
		if (userDaoSession == null) {
			if (userDaoMaster == null) {
				userDaoMaster = getUserDaoMaster(context,dbName);
			}
			userDaoSession = userDaoMaster.newSession();
		}
		return userDaoSession;
	}
	
	public static void clearUserDBConnect(){
		userDaoMaster = null;
		userDaoSession = null;
	}
}
