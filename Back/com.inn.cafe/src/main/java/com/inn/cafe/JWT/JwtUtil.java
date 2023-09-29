package com.inn.cafe.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Date;

@Service
public class JwtUtil {

// define a secret key
    private String secret = "dnipro";
    // method to extract the  user name from the token
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }
    // method to extract the expiration time
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }
// method to extract claim from the token

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    // to check if the token has expired or not
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    // method to pass the value inside this one
    public String generateToken (String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role); //"role":key role:value
        // now we gonna generate the token
        return createToken(claims,username); // username is a subject


    }

    // method to generate token we need to define the role
    private String createToken(Map<String, Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // u can change subject to username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10)) //this token will be expired in ten hour
                .signWith(SignatureAlgorithm.HS256,secret).compact();

    }

    // method to validate the token :1 check if the user name is valid && check is tiken expire
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}
