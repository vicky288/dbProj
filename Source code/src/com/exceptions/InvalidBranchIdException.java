package com.exceptions;

public class InvalidBranchIdException extends Exception{
	public InvalidBranchIdException(){
		super("Exception::Invalid Branch");
	}
	
	public String toString(){
		return("Invalid BranchId Exception");
	}
	
}
