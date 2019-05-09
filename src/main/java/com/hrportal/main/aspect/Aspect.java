package com.hrportal.main.aspect;

import java.security.Key;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hrportal.main.exception.PortalException;
import com.hrportal.main.exception.PortalUnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

	public Aspect() {

	}

	@Autowired
	Key key;

	@Autowired
	Environment env;
	
	/**
	 * Annotations for authentication
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(Authenticate)")
	public Object authenticateUser(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[]           args    = joinPoint.getArgs();
		HttpServletRequest request = null;
		for (Object arg : args) {
			if (arg instanceof ServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}

		String sessionId = getSessionId(request);
		String userName  = "";
		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(sessionId);

			userName = (String) jws.getBody().get("username");

		} catch (Exception e) {
			throw new PortalUnAuthorizedException(e, "UnAuthorized token is in-valid");
		}
		if (userName.equals(env.getProperty("hr.username"))) {

			return joinPoint.proceed();

		} else {
			throw new PortalUnAuthorizedException("not authroized");

		}

	}
	/**
	 * Utility to get session id header
	 * @param request
	 * @return
	 */
	private String getSessionId(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			throw new PortalUnAuthorizedException("portalSessionId cookie not available");
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("sessionId")) {
				return cookie.getValue();
			}
		}
		throw new PortalUnAuthorizedException("portalSessionId cookie not available");
	}

}
