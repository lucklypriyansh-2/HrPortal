package com.hrportal.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class PortalException extends RuntimeException{

	
	public PortalException(String msg)
	{
		super(msg);
	}
	
	public PortalException(Throwable e,String msg)
	{
		super(msg,e);
	}
}
