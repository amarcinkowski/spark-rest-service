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

		post("/companies",
				(req, res) -> companyService.createCompany(req.queryParams("name"), req.queryParams("address"),
						req.queryParams("city"), req.queryParams("country"), req.queryParams("e­mail"),
						req.queryParams("phoneNumber")), json());

		after((req, res) -> {
			res.type("application/json");
		});
	}

}
