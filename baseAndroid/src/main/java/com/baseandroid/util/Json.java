package com.baseandroid.util;


public abstract class Json {
	private static Json json;

	Json() {}

	/**
	 * default json handler is alibaba fastjson.
	 * @return Json
	 */
	public static Json get() {
		if (json == null) {
			json = new GsonImpl();
		}
		return json;
	}

	public abstract String toString(Object src);

	public abstract <T> T toObject(String json, Class<T> claxx);

	public abstract <T> T toObject(byte[] bytes, Class<T> claxx);
}
