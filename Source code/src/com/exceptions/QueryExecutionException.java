package com.exceptions;

public class QueryExecutionException extends Exception{
	public QueryExecutionException(){
		super("Exception::QueryExecutionException");
	}
	public String toString(){
		return ("QueryExecutionException") ;
	}
}
