package com.github.amarcinkowski.sparkrestservice;

import org.junit.Before;

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

	@Before
	public void before() {
		companyService = new CompanyService();
	}

	CompanyService companyService;

	/**
	 * Test get.
	 */
	public void testGet() {
		Company c = companyService.getCompany(1L);
		assertTrue(c != null);
	}

	public void testAdd() {
		companyService.createCompany("Animex", "5th Ave", "NYC", "USA", "office@animex.com", "+995425374");
	}
}
