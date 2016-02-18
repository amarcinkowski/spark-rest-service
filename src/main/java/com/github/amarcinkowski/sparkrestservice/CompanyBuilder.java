package com.github.amarcinkowski.sparkrestservice;

import java.util.HashSet;
import java.util.Set;

import spark.Request;

/**
 * The Class CompanyBuilder.
 */
public class CompanyBuilder {

	/** The c. */
	private Company c;

	/**
	 * Instantiates a new company builder.
	 */
	public CompanyBuilder() {
	}

	/**
	 * From request.
	 *
	 * @param req the req
	 * @return the company builder
	 */
	public CompanyBuilder fromRequest(Request req) {
		Company company = new Company();
		company.setName(req.queryParams("name"));
		company.setAddress(req.queryParams("address"));
		company.setCity(req.queryParams("city"));
		company.setCountry(req.queryParams("country"));
		company.setE­mail(req.queryParams("e­mail"));
		company.setPhoneNumber(req.queryParams("phoneNumber"));
		Set<String> owners = new HashSet<>(
				java.util.Arrays.asList(new String[] { req.queryParams("beneficialOwner") }));
		company.setBeneficialOwner(owners);
		this.c = company;
		System.out.println(company.getBeneficialOwner() + company.getName());
		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the company
	 */
	public Company build() {
		return this.c;
	};

}
