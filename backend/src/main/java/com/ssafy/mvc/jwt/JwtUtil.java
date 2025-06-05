package com.ssafy.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key = "SSAFY_BigRoot_Final_Project_CliMong";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

	public String createToken(String name, int id, int manager) {
	    Date exp = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4); // 4시간
	    return Jwts.builder()
	            .header().add("typ", "JWT").and()
	            .claim("name", name)  // 한글을 포함한 이름
	            .claim("id", id)
	            .claim("manager", manager)
	            .expiration(exp)
	            .signWith(secretKey)
	            .compact();
	}
	
	public Jws<Claims> validate(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
	
}
