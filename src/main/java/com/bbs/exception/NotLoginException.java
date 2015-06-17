package com.bbs.exception;

public class NotLoginException extends Exception {
	
	public NotLoginException(String errorMsg){
		super(errorMsg);
	}
}
