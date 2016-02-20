package com.github.amarcinkowski.sparkrestservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import spark.Spark;
import spark.utils.IOUtils;

/**
 * The Class CompanyServiceTest.
 */
public class CompanyServiceTest {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CompanyServiceTest.class);

	/** The Constant PORT. */
	private final static String PORT = "4567";

	/** The Constant URL. */
	private static final String URL = "http://localhost:" + PORT + "/companies";

	/** The Constant JSON. */
	public final static String JSON = "{\"name\": \"Andrzej Marcinkowski IT Services\","
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	/** The Constant JSON_NO_NAME. */
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
		Spark.awaitInitialization();
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
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testAdd() throws IOException {
		LOGGER.trace("testAdd");
		TestResponse res = request("POST", JSON, URL);
		Map<String, Object> json = res.json();
		assertEquals(200, res.status);
		assertEquals("Andrzej Marcinkowski IT Services", json.get("name"));
		assertEquals("Armii Krajowej 41", json.get("address"));
		assertNotNull(json.get("companyID"));
	}

	@Test
	public void testGetAll() throws IOException {
		LOGGER.trace("testGet");
		request("POST", JSON, URL);
		TestResponse res = request("GET", null, URL);
		assertEquals(200, res.status);
		assertTrue(res.body.contains("Andrzej Marcinkowski"));
		Map[] json = res.jsonArray();
		assertEquals("Andrzej Marcinkowski IT Services", json[0].get("name"));
	}

	@Test
	public void testGetByID() throws IOException {
		LOGGER.trace("testGet");
		TestResponse res1 = request("POST", JSON, URL);
		Object companyId = res1.json().get("companyID");
		TestResponse res = request("GET", null, URL + "/" + companyId);
		assertEquals(200, res.status);
		assertTrue(res.body.contains("Andrzej Marcinkowski"));
		assertEquals("Andrzej Marcinkowski IT Services", res.json().get("name"));
	}

	/**
	 * Test validators.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testValidators() throws IOException {
		LOGGER.trace("testValidators");
		TestResponse res = request("POST", JSON_NO_NAME, URL);
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
	 * @param urlString
	 *            the url string
	 * @return the test response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private TestResponse request(String method, String json, String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		if (json != null) {
			connection.getOutputStream().write(json.getBytes());
		}
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
		@SuppressWarnings("unchecked")
		public Map<String, Object> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Map[] jsonArray() {
			return new Gson().fromJson(body, HashMap[].class);
		}

	}

}
