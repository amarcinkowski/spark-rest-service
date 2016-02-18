package com.github.amarcinkowski.sparkrestservice;

@SuppressWarnings("serial")
public class AlreadyExistsException extends Exception {

	public AlreadyExistsException(String msg) {
		super(msg);
	}
	
}
