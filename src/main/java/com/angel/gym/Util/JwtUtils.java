package com.angel.gym.Util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtils {

	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	@Value("${security.jwt.user.generator}")
	private String userGenerator;
	
	
	public String createToken(Authentication authentication) { // CREAR TOKEN

		
		Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
		
		String username = authentication.getPrincipal().toString();
		
		String authorities = authentication.getAuthorities()
				.stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority())
				.collect(Collectors.joining(","));
		
		String jwtToken = JWT.create()
				.withIssuer(this.userGenerator)
				.withSubject(username)
				.withClaim("authorities", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(algorithm);
		
		return jwtToken;
		
	}
	
	public DecodedJWT validateToken(String token) { // VALIDAR EL TOKEN
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
			
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(this.userGenerator)
					.build();
			
			DecodedJWT decoded = verifier.verify(token);
			
			return decoded;
			
		} catch (JWTVerificationException e) {
			
			throw new JWTVerificationException("Token invalid, not authorized");
			
		}
		
	}
	
	public String extractUsername(DecodedJWT decodedJWT) { // EXTRAER EL USUARIO QUE VENDRÁ DENTRO DE TOKEN
		
		return decodedJWT.getSubject().toString();
		
	}
	
	public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) { // EXTRAER UN CLAIM ESPECIFICO
		
		return decodedJWT.getClaim(claimName);
		
	}
	
	public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) { //EXTRAER TODOS LOS CLAIMS
		
		return decodedJWT.getClaims();
		
	}
	
}
