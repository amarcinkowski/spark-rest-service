package com.github.amarcinkowski.sparkrestservice;

/**
 * The Class AlreadyExistsException.
 */
@SuppressWarnings("serial")
public class AlreadyExistsException extends Exception {

	/**
	 * Instantiates a new already exists exception.
	 *
	 * @param msg the msg
	 */
	public AlreadyExistsException(String msg) {
		super(msg);
	}
	
}
