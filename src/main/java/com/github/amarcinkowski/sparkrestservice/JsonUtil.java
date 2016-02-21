package com.github.amarcinkowski.sparkrestservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import spark.ResponseTransformer;

/**
 * The Class JsonUtil.
 */
public class JsonUtil {

	/** The gson. */
	private static Gson gson;

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	private static Gson getGson() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
			gson = gsonBuilder.create();
		}
		return gson;
	}

	/**
	 * To json.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		return getGson().toJson(object);
	}

	/**
	 * Parses the json.
	 *
	 * @param json
	 *            the json
	 * @return the company
	 */
	public static Company parse(String json) {
		return getGson().fromJson(json, Company.class);
	}

	/**
	 * Json.
	 *
	 * @return the response transformer
	 */
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
	
}