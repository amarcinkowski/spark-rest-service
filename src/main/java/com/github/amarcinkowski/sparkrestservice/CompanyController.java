package com.github.amarcinkowski.sparkrestservice;

import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.getId;
import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.parseAndValidate;
import static com.github.amarcinkowski.sparkrestservice.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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

		get("/companies/:id", (req, res) -> companyService.getCompany(getId(req)), json());

		post("/companies", (req, res) -> companyService.addNewCompany(parseAndValidate(req)), json());

		put("/companies", (req, res) -> companyService.updateCompany(parseAndValidate(req)), json());

		after((req, res) -> {
			res.type("application/json");
		});
	}

}