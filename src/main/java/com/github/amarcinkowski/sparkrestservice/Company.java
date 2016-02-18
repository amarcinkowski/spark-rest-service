package com.github.amarcinkowski.sparkrestservice;

import java.util.Set;

/**
 * The Class Company.
 */
public class Company {

	/** The company id. */
	private Long companyID;

	/** The name. */
	private String name;

	/** The address. */
	private String address;

	/** The city. */
	private String city;

	/** The country. */
	private String country;

	/** The e­mail (not required). */
	private String e­mail;

	/** The phone number (not required). */
	private String phoneNumber;

	/** The beneficial owners. */
	Set<String> beneficialOwner;

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public Long getCompanyID() {
		return companyID;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyID
	 *            the new company id
	 */
	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address
	 *            the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the e­mail.
	 *
	 * @return the e­mail
	 */
	public String getE­mail() {
		return e­mail;
	}

	/**
	 * Sets the e­mail.
	 *
	 * @param e­mail
	 *            the new e­mail
	 */
	public void setE­mail(String e­mail) {
		this.e­mail = e­mail;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber
	 *            the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the beneficial owner.
	 *
	 * @return the beneficial owner
	 */
	public Set<String> getBeneficialOwner() {
		return beneficialOwner;
	}

	/**
	 * Sets the beneficial owner.
	 *
	 * @param beneficialOwner
	 *            the new beneficial owner
	 */
	public void setBeneficialOwner(Set<String> beneficialOwner) {
		this.beneficialOwner = beneficialOwner;
	}

}
