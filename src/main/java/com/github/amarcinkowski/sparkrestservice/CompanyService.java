package com.github.amarcinkowski.sparkrestservice;

import static com.github.amarcinkowski.sparkrestservice.CompanyValidator.parseRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;

/**
 * The Class CompanyService.
 */
public class CompanyService {

	/** The companies. */
	private Map<Long, Company> companies = new HashMap<>();

	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 */
	public List<Company> getAll() {
		return new ArrayList<>(companies.values());
	}

	/**
	 * Gets the company.
	 *
	 * @param id            the id
	 * @return the company
	 * @throws NoSuchIdException the no such id exception
	 */
	public Company get(Long id) throws NoSuchIdException {
		if (companies.containsKey(id)) {
			return companies.get(id);
		}
		throw new NoSuchIdException("There is no company with this id");
	}

	/**
	 * Update company.
	 *
	 * @param company            the company
	 * @return the company
	 * @throws CannotUpdateException             the cannot update exception (it may not exist)
	 */
	public Company update(Company company) throws CannotUpdateException {
		if (!companies.containsKey(company.getCompanyID())) {
			throw new CannotUpdateException("There's no Company with this ID");
		}
		companies.put(company.getCompanyID(), company);
		return company;
	}

	/**
	 * Adds the beneficial owner.
	 *
	 * @param companyID            the company id
	 * @param owners            the name
	 * @return the company
	 * @throws CannotUpdateException the cannot update exception
	 */
	public Integer addOwners(Long companyID, String... owners) throws CannotUpdateException {
		Company c = companies.get(companyID);
		if (c == null) {
			throw new CannotUpdateException("There's no Company with this ID");
		}
		c.addBeneficialOwners(owners);
		return owners.length;
	}

	/**
	 * Adds the new.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the object
	 * @throws AlreadyExistsException the already exists exception
	 */
	public Object addNew(Request req, Response res) throws AlreadyExistsException {
		Company company = parseRequest(req);
		String locationHeader = String.format("http://%s/companies/%s", req.host(), company.getCompanyID());
		res.header("Location", locationHeader);
		res.status(201);
		if (companies.put(company.getCompanyID(), company) != null) {
			throw new AlreadyExistsException("Company under ID already exist (please update instead of add)");
		}
		return "Company created";
	}
}
