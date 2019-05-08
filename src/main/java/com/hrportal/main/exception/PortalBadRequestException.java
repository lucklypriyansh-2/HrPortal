package com.hrportal.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PortalBadRequestException  extends RuntimeException{

	
	public PortalBadRequestException(String msg,Throwable e)
	{
		super(msg,e);
	}

	public PortalBadRequestException(String msg) {
		super(msg);
	}

	
}
