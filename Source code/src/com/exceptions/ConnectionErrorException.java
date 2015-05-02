package com.exceptions;

public class ConnectionErrorException extends Exception{
	public ConnectionErrorException(){
		super("Exception::Connection Error!!!");
	}
	public String toString(){
		return ("Connection Error Exception") ;
	}
}
