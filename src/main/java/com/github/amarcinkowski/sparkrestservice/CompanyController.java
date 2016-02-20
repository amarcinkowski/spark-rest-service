package com.github.amarcinkowski.sparkrestservice;

import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.getId;
import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.parseAndValidate;
import static com.github.amarcinkowski.sparkrestservice.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The Class CompanyController.
 */
public class CompanyController {

	/** The Constant logger. */
	private final static Logger logger = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * Instantiates a new company controller.
	 *
	 * @param companyService
	 *            the company service
	 */
	public CompanyController(final CompanyService companyService) {

		staticFileLocation("/public");

		get("/companies", (req, res) -> companyService.getAllCompanies(), json());

		get("/companies/:id", (req, res) -> companyService.getCompany(getId(req)), json());

		post("/companies", (req, res) -> companyService.addNewCompany(parseAndValidate(req)), json());

		put("/companies", (req, res) -> companyService.updateCompany(parseAndValidate(req)), json());

		after((req, res) -> {
			res.type("application/json");
		});

		exception(Exception.class, (e, req, res) -> {
			res.status(400);
			logger.warn(e.getMessage());
			res.body(new Gson().toJson(e.getMessage()));
		});
	}

}
