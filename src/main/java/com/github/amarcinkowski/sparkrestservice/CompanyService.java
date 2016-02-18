package com.github.amarcinkowski.sparkrestservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Company> getAllCompanies() {
		return new ArrayList<>(companies.values());
	}

	/**
	 * Gets the company.
	 *
	 * @param id
	 *            the id
	 * @return the company
	 */
	public Company getCompany(Long id) {
		return companies.get(id);
	}

	/**
	 * Adds the company.
	 *
	 * @param company
	 *            the company
	 * @throws AlreadyExistsException
	 *             the already exists exception if companyId already registered
	 *             (should use update instead of add new)
	 */
	public Company addNewCompany(Company company) throws AlreadyExistsException {
		if (companies.put(company.getCompanyID(), company) != null) {
			throw new AlreadyExistsException("Company under ID already exist (please update instead of add)");
		}
		return company;
	}

	/**
	 * Update company.
	 *
	 * @param company
	 *            the company
	 * @throws CannotUpdateException
	 *             the cannot update exception (it may not exist)
	 */
	public Company updateCompany(Company company) throws CannotUpdateException {

		if (!companies.containsKey(company.getCompanyID())) {
			throw new CannotUpdateException("There's no Company with this ID");
		}
		companies.put(company.getCompanyID(), company);
		return company;
	}

	/**
	 * Adds the beneficial owner.
	 *
	 * @param companyID
	 *            the company id
	 * @param name
	 *            the name
	 * @return the company
	 * @throws CannotUpdateException
	 */
	public void addBeneficialOwner(Long companyID, String name) throws CannotUpdateException {
		Company c = companies.get(companyID);
		if (c == null) {
			throw new CannotUpdateException("There's no Company with this ID");
		}
		c.addBeneficialOwner(name);
	}
}
