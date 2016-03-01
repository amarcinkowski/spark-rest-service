package com.github.amarcinkowski.sparkrestservice;

import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.getId;
import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.getOwners;
import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.parseRequest;
import static com.github.amarcinkowski.sparkrestservice.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
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
	private final static Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * Instantiates a new company controller.
	 *
	 * @param service
	 *            the company service
	 */
	public CompanyController(final CompanyService service) {

		ipAddress(getCloudIP());

		port(getCloudAssignedPort());

		staticFileLocation("/public");

		get("/companies", (req, res) -> service.getAll(), json());

		get("/companies/:id", (req, res) -> service.get(getId(req)), json());

		post("/companies", (req, res) -> service.addNew(req, res), json());

		put("/companies", (req, res) -> service.update(parseRequest(req)), json());

		put("/companies/owners/:id", (req, res) -> service.addOwners(getId(req), getOwners(req)), json());

		after((req, res) -> {
			res.type("application/json");
		});

		exception(Exception.class, (e, req, res) -> {
			switch (e.getClass().getSimpleName()) {
			case "ConstraintViolationException":
				res.status(400);
				break;
			case "CannotUpdateException":
			case "NoSuchIdException":
				res.status(404);
				break;
			case "AlreadyExistsException":
				res.status(409);
				break;
			default:
				res.status(400);
			}
			LOGGER.trace(e.getMessage());
			res.body(new Gson().toJson(e.getMessage()));
		});
	}

	private String getCloudIP() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			// Openshift IP
			return processBuilder.environment().get("OPENSHIFT_DIY_IP");
		}
		return "0.0.0.0";
	}

	/**
	 * Gets the heroku assigned port.
	 *
	 * @return the heroku assigned port
	 */
	static int getCloudAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			// Heroku
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		} else if (processBuilder.environment().get("OPENSHIFT_DIY_PORT") != null) {
			// Openshift
			return Integer.parseInt(processBuilder.environment().get("OPENSHIFT_DIY_PORT"));
		}
		return 4567;
	}

}
