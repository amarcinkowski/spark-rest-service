package com.github.amarcinkowski.sparkrestservice;

import static spark.Spark.*;
import static com.github.amarcinkowski.sparkrestservice.JsonUtil.*;

/**
 * The Class CompanyController.
 */
public class CompanyController {

	/**
	 * Instantiates a new company controller.
	 *
	 * @param companyService
	 *            the company service
	 */
	public CompanyController(final CompanyService companyService) {
		
		get("/companies", (req, res) -> companyService.getAllCompanies(), json());

		after((req, res) -> {
			res.type("application/json");
		});
	}

}
