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
	 * Creates the company.
	 *
	 * @param name
	 *            the name
	 * @param address
	 *            the address
	 * @param city
	 *            the city
	 * @param country
	 *            the country
	 * @param e­mail
	 *            the e­mail
	 * @param phoneNumber
	 *            the phone number
	 * @return the company
	 */
	public Company createCompany(String name, String address, String city, String country, String e­mail,
			String phoneNumber) {
		Company c = new Company();
		c.setName(name);
		c.setAddress(address);
		c.setCity(city);
		c.setCountry(country);
		c.setE­mail(e­mail);
		c.setPhoneNumber(phoneNumber);
		companies.put(c.getCompanyID(), c);
		return c;
	}

	/**
	 * Update company.
	 *
	 * @param companyID
	 *            the company id
	 * @param name
	 *            the name
	 * @param address
	 *            the address
	 * @param city
	 *            the city
	 * @param country
	 *            the country
	 * @param e­mail
	 *            the e­mail
	 * @param phoneNumber
	 *            the phone number
	 * @return the company
	 */
	public Company updateCompany(Long companyID, String name, String address, String city, String country,
			String e­mail, String phoneNumber) {
		Company c = companies.get(companyID);
		c.setName(name);
		c.setAddress(address);
		c.setCity(city);
		c.setCountry(country);
		c.setE­mail(e­mail);
		c.setPhoneNumber(phoneNumber);
		return companies.put(c.getCompanyID(), c);
	}

	/**
	 * Adds the beneficial owner.
	 *
	 * @param companyID
	 *            the company id
	 * @param name
	 *            the name
	 * @return the company
	 */
	public Company addBeneficialOwner(Long companyID, String name) {
		return companies.get(companyID).addBeneficialOwner(name);
	}
}
