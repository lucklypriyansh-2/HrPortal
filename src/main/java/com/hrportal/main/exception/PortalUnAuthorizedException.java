package com.hrportal.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class PortalUnAuthorizedException extends RuntimeException{

	
	public PortalUnAuthorizedException(String msg)
	{
		super(msg);
	}
	
	public PortalUnAuthorizedException(Exception e,String msg)
	{
		super(msg,e);
	}
}
