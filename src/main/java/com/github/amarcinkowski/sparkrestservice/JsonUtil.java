package com.github.amarcinkowski.sparkrestservice;

import com.google.gson.Gson;

import spark.ResponseTransformer;

/**
 * The Class JsonUtil.
 */
public class JsonUtil {
	
	/**
	 * Private constructor.
	 */
	private JsonUtil() {
	}

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}
	
	/**
	 * Parses the json.
	 *
	 * @param json the json
	 * @return the company
	 */
	public static Company parse(String json) {
		return new Gson().fromJson(json, Company.class);
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