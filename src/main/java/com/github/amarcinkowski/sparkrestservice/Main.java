package com.github.amarcinkowski.sparkrestservice;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new CompanyController(new CompanyService());
	}
	
}
