package com.exceptions;

public class CheckInDateNotValidException extends Exception{
	public CheckInDateNotValidException() {
		super("Exception:: Check-in date should be after Check-out date.");
	}
	public String toString() {
		return("CheckInDateNotValidException");
	}
}
