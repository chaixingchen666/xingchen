package com.flyang.yky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class SdkJsonParse {
	
	
	public static String toJsonString(Object object){
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	

	public static Object[] jsonToObjects(String jsonStr) {
		java.lang.reflect.Type listType = new TypeToken<Object[]>() {
		}.getType();
		Gson gson = new Gson();	
		Object[] result = gson.fromJson(jsonStr.toString(), listType);
		if (result == null) {
			result = new Object[] {};
		}
		return result;
	}
	
	public static JsonObject toJsonObject(String jsonStr){
		/*	java.lang.reflect.Type listType = new TypeToken<JsonObject>() {
		}.getType();
		Gson gson = new Gson();	
		JsonObject result = gson.fromJson(jsonStr, listType);
		if (result == null) {
			result = new JsonObject();
		}
		return result;*/
		JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
		return jsonObject;
		
	}
	
	public static Map<String, Object> jsonToMap(Object jsonStr) {
		java.lang.reflect.Type listType = new TypeToken<Map<String, Object>>() {
		}.getType();
		Gson gson = new Gson();	
		Map<String, Object> result = gson.fromJson(jsonStr.toString(), listType);
		if (result == null) {
			result = new HashMap<>();
		}
		return result;
	}
	
	public static <T> T jsonToClass(String string,Class<T> c){
		T t = null;
		try {
			t = c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson mGson = new Gson();
		return mGson.fromJson(string, (Class<T>) t);
	}

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> jsonToObject(String string,Class<T> c){
				Gson mGson = new Gson();
				return mGson.fromJson(string, ArrayList.class);
		 //return (ArrayList<T>)o;
	}
}
