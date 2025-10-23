package vn.com.fortis.service.impl;

import vn.com.fortis.constant.TokenType;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.repository.InvalidatedTokenRepository;
import vn.com.fortis.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j(topic = "JWT-SERVICE")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiryHour}")
    long expiryHour;

    @Value("${jwt.expiryDay}")
    long expiryDay;

    @Value("${jwt.accessKey}")
    String accessKey;

    @Value("${jwt.refreshKey}")
    String refreshKey;

    final InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public String generateAccessToken(String userId, String username, Collection<? extends GrantedAuthority> authorities) {
//        log.info("Generate access token for user{} with authorities{}", username, authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return generateAccessToken(claims, username);
    }

    @Override
    public String generateRefreshToken(String userId, String username, Collection<? extends GrantedAuthority> authorities) {
//        log.info("Generate refresh token for user{} with authorities{}", username, authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return generateRefreshToken(claims, username);
    }

    @Override
    public String extractUserName(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, TokenType type, String name) {
        final String username = extractUserName(token, type);
        String jwtId = extractTokenId(token, type);

        boolean isInvalidated = invalidatedTokenRepository.existsById(jwtId);

        return username.equals(name) && !isTokenExpired(token, type) && !isInvalidated;
    }

    @Override
    public boolean isExpired(String token, TokenType type) {
        return isTokenExpired(token, type);
    }

    private String generateAccessToken(Map<String, Object> claims, String username) {
//        log.info("------------- [ generateAccessToken]------------------");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, String username) {
//        log.info("------------- [ generateRefreshToken]------------------");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(UUID.randomUUID().toString()) // fixed
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryDay))
                .signWith(getKey(TokenType.REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
//        log.info("------------ [ getKey ] -------------------------");
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new InvalidDataException("Invalid Token type");
        }
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimsTFunction) {
//        log.info("------------- [ extractClaim ] ---------------");
        final Claims claims = extractAllClaims(token, type);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type) {
//        log.info("------------- [ extraAllClaims ] --------------------");
        try {
            return Jwts.parserBuilder().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("Extra all claim failed, message = {}", e.getMessage());
            throw new AccessDeniedException("Access denied: "+  e.getMessage());
        }
    }

    private boolean isTokenExpired(String token, TokenType type) {
        return extractTokenExpiration(token, type).before(new Date());
    }

    private Date extractTokenExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

    public String extractTokenId(String token, TokenType type) {
        return extractClaim(token, type, Claims::getId);
    }
}
