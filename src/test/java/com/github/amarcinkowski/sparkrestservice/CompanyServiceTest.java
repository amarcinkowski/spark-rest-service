package com.github.amarcinkowski.sparkrestservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import spark.Spark;
import spark.utils.IOUtils;

/**
 * The Class CompanyServiceTest.
 */
public class CompanyServiceTest {

	/** The Constant URL. */
	private static final String URL = "http://localhost:4567/companies";

	/** The Constant JSON. */
	public final static String JSON = "{\"name\": \"Andrzej Marcinkowski IT Services\","
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	public final static String JSON_NO_NAME = "{"
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	/**
	 * Before class.
	 */
	@BeforeClass
	public static void beforeClass() {
		Main.main(null);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * After class.
	 */
	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}

	/**
	 * Test add.
	 */
	@Test
	public void testAdd() throws IOException {
		TestResponse res = request("POST", JSON);
		Map<String, String> json = res.json();
		assertEquals(200, res.status);
		assertEquals("Andrzej Marcinkowski IT Services", json.get("name"));
		assertEquals("Armii Krajowej 41", json.get("address"));
		assertNotNull(json.get("companyID"));
	}

	@Test
	public void testValidators() throws IOException {
		TestResponse res = request("POST", JSON_NO_NAME);
		assertEquals(400, res.status);
		assertTrue(res.body.contains("response code: 400"));
	}

	/**
	 * Request.
	 *
	 * @param method
	 *            the method
	 * @param json
	 *            the json
	 * @return the test response
	 */
	private TestResponse request(String method, String json) throws IOException {
		URL url = new URL(URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		connection.getOutputStream().write(json.getBytes());
		connection.connect();
		String body;
		try {
			body = IOUtils.toString(connection.getInputStream());
		} catch (Exception e) {
			body = e.getMessage();
		}
		return new TestResponse(connection.getResponseCode(), body);
	}

	/**
	 * The Class TestResponse.
	 */
	private static class TestResponse {

		/** The body. */
		public final String body;

		/** The status. */
		public final int status;

		/**
		 * Instantiates a new test response.
		 *
		 * @param status
		 *            the status
		 * @param body
		 *            the body
		 */
		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		/**
		 * Json.
		 *
		 * @return the map
		 */
		public Map<String, String> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
	}

}
