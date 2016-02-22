package com.github.amarcinkowski.sparkrestservice;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

public class CompanyBuilderTest {

	@Test
	public void builderTest() {
		CompanyBuilder builder = new CompanyBuilder();
		Company c = builder.id(1L).required("Q", "W", "E", "R").additional("S", "T").owners("U", "W").build();
		assertEquals(c.getCompanyID(), new Long(1L));
		assertEquals(c.getName(), "Q");
		assertEquals(c.getAddress(), "W");
		assertEquals(c.getCity(), "E");
		assertEquals(c.getCountry(), "R");
		assertEquals(c.getMail(), "S");
		assertEquals(c.getPhoneNumber(), "T");
		assertEquals(c.getBeneficialOwner(), new HashSet<>(Arrays.asList(new String[] { "U", "W" })));
	}

}
