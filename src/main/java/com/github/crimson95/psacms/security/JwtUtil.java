package com.github.crimson95.psacms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Secret used to sign JWTs. In a real deployment, keep this in configuration or a secret manager.
    // A fixed key keeps existing tokens valid across application restarts.
    private static final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    // Token lifetime: 10 hours.
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    // Creates a signed JWT for a successfully authenticated user.
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Store the username as the token subject.
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Token creation time.
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Token expiration time.
                .signWith(SECRET_KEY)  // Sign the token so tampering can be detected.
                .compact();
    }

    // Reads the username stored in the JWT subject.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Checks that the token belongs to this user and has not expired.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
