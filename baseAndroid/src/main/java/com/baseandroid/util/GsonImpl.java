package com.baseandroid.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonImpl extends Json {
	private Gson gson = new GsonBuilder()
	.setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();

	@Override
	public String toString(Object src) {
		return gson.toJson(src);
	}

	@Override
	public <T> T toObject(String json, Class<T> claxx) {
		return gson.fromJson(json, claxx);
	}

	@Override
	public <T> T toObject(byte[] bytes, Class<T> claxx) {
		return gson.fromJson(new String(bytes), claxx);
	}

}
