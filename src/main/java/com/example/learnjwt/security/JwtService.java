package com.example.learnjwt.security;

import com.example.learnjwt.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expiration;


    public String generateToken(User user){
        // properties => claims
        Map<String, Object> claims = new HashMap<>();

        // Đưa các thuộc tính vào claims
        claims.put("email",user.getEmail());

        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(System.currentTimeMillis() + expiration);
        try{
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(currentTime)
                    .setExpiration(expirationTime)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception e){
            System.err.println("Cannot create jwt token: " + e.getMessage());
            return null;
        }
    }

    // Kiểm tra token có hợp lệ hay không
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        // Trả về true khi username trong token equals username trong userDetails và token còn hạn
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Kiểm tra hạn token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Lấy ra hạn token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Lấy email từ chuỗi jwt
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    // Lấy riêng 1 claims ta muốn
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Lấy ra tất cả claims
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
//        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
    }
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
