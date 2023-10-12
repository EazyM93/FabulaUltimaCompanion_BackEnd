package eazym.FabulaUltimaCompanion_BackEnd.security.config;

import eazym.FabulaUltimaCompanion_BackEnd.exceptions.UnauthorizedException;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTools {

    @Value("${spring.jwt.secret}")
    String secretKey;

    public String createToken(User user) {

        // directly return token
        return Jwts.builder()
                .setSubject(user.getIdUser().toString()) // token's owner
                .setIssuedAt(new Date(System.currentTimeMillis())) // token emission date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // expire time
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())) // generate token signature
                .compact();

    }

    public void verifyToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parse(token);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnauthorizedException("Invalid token! Please do login again");
        }

    }

    public String extractSubject(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

}
