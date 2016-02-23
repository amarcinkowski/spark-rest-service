package com.github.amarcinkowski.sparkrestservice;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class CompanyBuilder.
 */
public class CompanyBuilder {

	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The address. */
	private String address;
	
	/** The city. */
	private String city;
	
	/** The country. */
	private String country;
	
	/** The mail. */
	private String mail;
	
	/** The phone number. */
	private String phoneNumber;
	
	/** The owners. */
	private Set<String> owners = new HashSet<>();

	/**
	 * Instantiates a new company builder.
	 */
	public CompanyBuilder() {
	}

	/**
	 * From request.
	 *
	 * @param name the name
	 * @param address the address
	 * @param city the city
	 * @param country the country
	 * @return the company builder
	 */
	public CompanyBuilder required(String name, String address, String city, String country) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		return this;
	}

	/**
	 * Id.
	 *
	 * @param id the id
	 * @return the company builder
	 */
	public CompanyBuilder id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Additional.
	 *
	 * @param mail the mail
	 * @param phoneNumber the phone number
	 * @return the company builder
	 */
	public CompanyBuilder additional(String mail, String phoneNumber) {
		this.mail = mail;
		this.phoneNumber = phoneNumber;
		return this;
	}

	/**
	 * Owners.
	 *
	 * @param owners the owners
	 * @return the company builder
	 */
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