package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.JWTSecurityConfiguration;

@RestController
@RequestMapping("auth")
public class AuthController {
	
	@Autowired
	JWTSecurityConfiguration s;
	
	@GetMapping(path = "/token/")
	public ResponseEntity<String> getToken() {
		JwtClaimsSet claims = JwtClaimsSet.builder()
	            .issuer("https://somewhere.com")  //Only this for simplicity
	            .build();
		
		JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
		String token = s.jwtAccessTokenEncoder().encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

}
