package com.hrportal.main.controller;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrportal.main.model.LoginRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

@RestController
public class LoginController {

	@Autowired
	Environment env;

	@Autowired
	Key key;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest login)
			throws InvalidKeyException, UnsupportedEncodingException {
		if (login.getUserName().equals(env.getProperty("hr.username"))
				&& login.getPassword().equals(env.getProperty("hr.password"))) {

			String jws = Jwts.builder().setSubject("cred")
					.claim("username", env.getProperty("hr.username")).signWith(key).compact();

			ResponseEntity<Object> re = new ResponseEntity<Object>(jws, HttpStatus.OK);
			return re;
		}

		return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);

	}
}
