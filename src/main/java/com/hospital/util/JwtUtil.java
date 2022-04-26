package com.hospital.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secret;
	private int TOKEN_VALIDITY_IN_MINUTES=15;
	
	public boolean validateToken(String token,String username) {
		String tokenUserName = getSubject(token);
		return (tokenUserName.equals(username)&& !isTokenExp(token));
	}
	
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}
	
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}
	
	public String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("Jwt demo")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(TOKEN_VALIDITY_IN_MINUTES)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
