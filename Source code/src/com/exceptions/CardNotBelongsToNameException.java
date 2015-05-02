package com.exceptions;

public class CardNotBelongsToNameException extends Exception{
	public CardNotBelongsToNameException(){
		super("Exception:: Card doesn'y belong to the person.");
	}
	public String toString() {
		return("CardNotBelongsToNameException");
	}
}
