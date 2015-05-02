package com.exceptions;

public class QueryStringException extends Exception{
	public QueryStringException(){
		super("Exception::Query String is Null!!!");
	}
	public String toString(){
		return ("Query String Exception") ;
	}
}
