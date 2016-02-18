package com.github.amarcinkowski.sparkrestservice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The Class CompanyServiceTest.
 */
public class CompanyServiceTest extends TestCase {

	/**
	 * Instantiates a new company service test.
	 *
	 * @param testName
	 *            the test name
	 */
	public CompanyServiceTest(String testName) {
		super(testName);
	}

	/**
	 * Suite.
	 *
	 * @return the test
	 */
	public static Test suite() {
		return new TestSuite(CompanyServiceTest.class);
	}

	/**
	 * Test app.
	 */
	public void testGet() {
		CompanyService companyService = new CompanyService();
		Company c = companyService.getCompany(1L);
	}
}
