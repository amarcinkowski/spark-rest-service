package com.github.amarcinkowski.sparkrestservice;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Class Company.
 */
public class Company {

	/** The Constant EMAIL_PATTERN. */
	private static final String EMAIL_PATTERN = "[A-Za-z0-9+_.-]+@.+";

	/** The Constant PHONE_NUMBER_PATTERN. */
	private static final String PHONE_NUMBER_PATTERN = "[0-9 \\(\\)\\+\\-]{9,18}";

	/** The company id. */
	@NotNull
	private Long companyID;

	/** The name. */
	@NotBlank(message = "Name is mandatory")
	private String name;

	/** The address. */
	@NotBlank(message = "Address is mandatory")
	private String address;

	/** The city. */
	@NotBlank(message = "City is mandatory")
	private String city;

	/** The country. */
	@NotBlank(message = "Country is mandatory")
	private String country;

	/** The e­mail (not required). */
	@Pattern(regexp = EMAIL_PATTERN, message = "Wrong email format")
	private String mail;

	/** The phone number (not required). */
	@Pattern(regexp = PHONE_NUMBER_PATTERN, message = "Wrong phone format")
	private String phoneNumber;

	/** The beneficial owners. */
	@NotEmpty
	Set<String> beneficialOwner = new HashSet<>();

	/**
	 * Instantiates a new company.
	 */
	public Company() {
		companyID = UUID.randomUUID().getLeastSignificantBits();
	}

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
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
	 *            the new beneficial owners
	 */
	public void setBeneficialOwner(Set<String> beneficialOwner) {
		this.beneficialOwner = beneficialOwner;
	}

	/**
	 * Adds the beneficial owners.
	 *
	 * @param beneficialOwners the beneficial owners
	 * @return the company
	 */
	public Company addBeneficialOwners(String... beneficialOwners) {
		for (String owner : beneficialOwners) {
			this.beneficialOwner.add(owner);
		}
		return this;
	}

}
