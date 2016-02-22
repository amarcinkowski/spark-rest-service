package com.github.amarcinkowski.sparkrestservice;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class CompanyBuilder.
 */
public class CompanyBuilder {

	private Long id;
	private String name;
	private String address;
	private String city;
	private String country;
	private String mail;
	private String phoneNumber;
	private Set<String> owners = new HashSet<>();

	/**
	 * Instantiates a new company builder.
	 */
	public CompanyBuilder() {
	}

	/**
	 * From request.
	 *
	 * @param req
	 *            the req
	 * @return the company builder
	 */
	public CompanyBuilder required(String name, String address, String city, String country) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		return this;
	}

	public CompanyBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public CompanyBuilder additional(String mail, String phoneNumber) {
		this.mail = mail;
		this.phoneNumber = phoneNumber;
		return this;
	}

	public CompanyBuilder owners(String... owners) {
		for (String owner : owners) {
			this.owners.add(owner);
		}
		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the company
	 */
	public Company build() {
		Company c = new Company();
		c.setCompanyID(id);
		c.setName(name);
		c.setAddress(address);
		c.setCity(city);
		c.setCountry(country);
		c.setMail(mail);
		c.setPhoneNumber(phoneNumber);
		c.setBeneficialOwner(owners);
		return c;
	};

}