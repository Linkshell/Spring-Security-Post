package com.yxy.code.utils.jjwt;

import io.jsonwebtoken.*;

public class JwtUtils {
    public static String CreateJwt(String uId){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(uId)
                .signWith(SignatureAlgorithm.HS256, "11111");
        String token = jwtBuilder.compact();
        return token;
    }

    public static Claims ParseJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey("11111")
                .parseClaimsJws(token)
                .getBody();
       return claims;
    }

}
