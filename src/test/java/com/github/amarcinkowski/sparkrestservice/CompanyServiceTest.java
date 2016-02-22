package com.github.amarcinkowski.sparkrestservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
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

	@Rule
	public TestRule watcher = new TestWatcher() {
		protected void starting(Description description) {
			LOGGER.info("===  Test: " + description.getMethodName());
		}
	};

	/** The Constant PORT. */
	private final static String PORT = "4567";

	/** The Constant URL. */
	private static final String URL = "http://localhost:" + PORT + "/companies";

	/** The Constant JSON. */
	public final static String JSON = "{\"name\": \"Andrzej Marcinkowski IT Services\","
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	public final static String JSON_SAME_ID = "{\"companyID\": 1, \"name\": \"Andrzej Marcinkowski IT Services\","
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	/** The Constant JSON_NO_NAME. */
	public final static String JSON_NO_NAME = "{"
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	/** The Constant JSON_NONEXISTING. */
	public final static String JSON_NONEXISTING = "{\"companyID\": 99, \"name\": \"Andrzej Marcinkowski IT Services\","
			+ "\"address\" : \"Armii Krajowej 41\",\"city\": \"Kalisz\",\"country\" : \"Poland\","
			+ "\"phone\" : \"+48 745634543\",\"beneficialOwner\" : [\"Andrzej Marcinkowski\", "
			+ "\"Emil i Lönneberga\", \"Mary Poppins\"]}";

	/** The Constant JSON_NO_NAME. */
	public final static String JSON_OWNERS = "[\"De vilde Svaner\", \"Den lille Havfrue\"]";

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
		TestResponse res = request("POST", JSON, URL);
		assertEquals(201, res.status);
		assertTrue(res.headers.containsKey("Location"));
		List<String> location = res.headers.get("Location");
		assertTrue(location.iterator().next().contains("http://"));
	}

	/**
	 * Test add already existing id.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testAddAlreadyExistingID() throws IOException {
		request("POST", JSON_SAME_ID, URL);
		TestResponse res2 = request("POST", JSON_SAME_ID, URL);
		assertEquals(409, res2.status);
	}

	/**
	 * Test get all.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetAll() throws IOException {
		request("POST", JSON, URL);
		TestResponse res = request("GET", null, URL);
		assertEquals(200, res.status);
		assertTrue(res.body.contains("Andrzej Marcinkowski"));
		Map[] json = res.jsonArray();
		assertEquals("Andrzej Marcinkowski IT Services", json[0].get("name"));
	}

	/**
	 * Test get by id.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetByID() throws IOException {
		TestResponse res1 = request("POST", JSON, URL);
		Object companyId = getIdFromHeader(res1);
		TestResponse res = request("GET", null, URL + "/" + companyId);
		assertEquals(200, res.status);
		assertTrue(res.body.contains("Andrzej Marcinkowski"));
		assertEquals("Andrzej Marcinkowski IT Services", res.json().get("name"));
	}

	@Test
	public void testGetNonexistent() throws IOException {
		TestResponse res = request("GET", null, URL + "/99");
		assertEquals(404, res.status);
	}

	/**
	 * Test update.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUpdate() throws IOException {
		TestResponse res1 = request("POST", JSON, URL);
		String companyId = getIdFromHeader(res1);
		TestResponse res2 = request("GET", null, URL + "/" + companyId);
		Company c = new Gson().fromJson(res2.body, Company.class);
		c.setCountry("Denmark");
		String json = new Gson().toJson(c);
		TestResponse res = request("PUT", json, URL);
		assertEquals(200, res.status);
		assertEquals("Denmark", res.json().get("country"));
	}

	/**
	 * Test update nonexisting.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUpdateNonexisting() throws IOException {
		TestResponse res = request("PUT", JSON_NONEXISTING, URL);
		assertEquals(404, res.status);
	}

	/**
	 * Test add owner.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testAddOwner() throws IOException {
		TestResponse res1 = request("POST", JSON, URL);
		String companyId = getIdFromHeader(res1);
		request("PUT", JSON_OWNERS, URL + "/owners/" + companyId);
		TestResponse res2 = request("GET", null, URL + "/" + companyId);
		Map<String, Object> json = res2.json();
		ArrayList<String> owners = (ArrayList<String>) json.get("beneficialOwner");
		assertEquals(5, owners.size());
	}

	String getIdFromHeader(TestResponse res) {
		List<String> location = res.headers.get("Location");
		String companyId = location.get(0).replace(URL + "/", "");
		return companyId;
	}

	@Test
	public void testAddOwnerToNonexistent() throws IOException {
		TestResponse res2 = request("PUT", JSON_OWNERS, URL + "/owners/99");
		assertEquals(404, res2.status);
	}

	/**
	 * Test validators.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testValidators() throws IOException {
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
			logReqRes(connection, body, json);
		} catch (Exception e) {
			body = e.getMessage();
			logReqRes(connection, body, json);
		}
		return new TestResponse(connection.getResponseCode(), body, connection.getHeaderFields());
	}

	/**
	 * Log req res.
	 *
	 * @param conn
	 *            the conn
	 * @param body
	 *            the body
	 * @param json
	 *            the json
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void logReqRes(HttpURLConnection conn, String body, String json) throws IOException {
		LOGGER.info(String.format("> %16s: %s", conn.getRequestMethod(), json));
		LOGGER.info(String.format("< %11s(%s): %s", conn.getResponseMessage(), conn.getResponseCode(), body));
	}

	/**
	 * The Class TestResponse.
	 */
	private static class TestResponse {

		/** The body. */
		public final String body;

		/** The status. */
		public final int status;

		private Map<String, List<String>> headers;

		/**
		 * Instantiates a new test response.
		 *
		 * @param status
		 *            the status
		 * @param body
		 *            the body
		 * @param map
		 */
		public TestResponse(int status, String body, Map<String, List<String>> headers) {
			this.headers = headers;
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

		/**
		 * Json array.
		 *
		 * @return the map[]
		 */
		@SuppressWarnings({ "rawtypes" })
		public Map[] jsonArray() {
			return new Gson().fromJson(body, HashMap[].class);
		}

	}

}
