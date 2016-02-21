package com.github.amarcinkowski.sparkrestservice;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import spark.Request;

/**
 * The Class CompanyValidator.
 */
public class CompanyValidator {

	/** The validator. */
	private static Validator validator;

	/**
	 * Parses the.
	 *
	 * @param req
	 *            the req
	 * @return the company
	 */
	private static Company parse(Request req) {
		Company company = JsonUtil.parse(req.body());
		return company;
	}

	/**
	 * Validate.
	 *
	 * @param company
	 *            the company
	 * @return the sets the
	 */
	private static Set<ConstraintViolation<Company>> validate(Company company) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		Set<ConstraintViolation<Company>> constraintViolations = validator.validate(company);
		return constraintViolations;
	}

	/**
	 * Parses the and validate.
	 *
	 * @param req
	 *            the req
	 * @return the company
	 */
	public static Company parseRequest(Request req) throws ConstraintViolationException {
		Company company = parse(req);
		Set<ConstraintViolation<Company>> constraintViolations = validate(company);
		if (constraintViolations.size() > 0) {
			String errorMsg = getErrorMessage(constraintViolations);
			throw new ConstraintViolationException(errorMsg, constraintViolations);
		}
		return company;
	}

	/**
	 * Gets the id.
	 *
	 * @param req
	 *            the req
	 * @return the id
	 */
	public static Long getId(Request req) {
		return Long.parseLong(req.params(":id"));
	}

    /**
     * Gets the owners.
     *
     * @param req the req
     * @return the owners
     */
    public static String[] getOwners(Request req) {
    	return JsonUtil.parseOwners(req.body());
	}
	
	/**
	 * Gets the error message.
	 *
	 * @param constraintViolations
	 *            the constraint violations
	 * @return the error message
	 */
	private static String getErrorMessage(Set<ConstraintViolation<Company>> constraintViolations) {
		StringBuilder builder = new StringBuilder();
		Iterator<ConstraintViolation<Company>> i = constraintViolations.iterator();
		while (i.hasNext()) {
			ConstraintViolation<Company> o = i.next();
			builder.append(o.getPropertyPath().toString().toUpperCase());
			builder.append(" ");
			builder.append(o.getMessage());
			builder.append("\n");
		}
		return builder.toString();
	}

}
