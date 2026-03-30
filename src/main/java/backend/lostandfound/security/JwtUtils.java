package backend.lostandfound.security;

import backend.lostandfound.model.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String SECRET="jdflidsjfskksddflkjsflijsfklrjflsdjflsdfjsdhslfjdhflsdfheslfhskldjfskdjfhjskdfhdskjfhdskfjdskjfdsfljdjflidjflsdjfl";
private final long EXPIRATION_TIME=1000*60*60;
private final Key SECRET_KEY= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
public String generateToken(String username, Long regNo, Role role){
    return Jwts.builder()
            .setSubject(username)
            .claim("regNo",regNo)
            .claim("role",role)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();

}

public String extractUsername(String token){
    return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

}
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateToken (String token) throws JwtException {
        try {
            extractUsername(token);
            return true;
        } catch (JwtException e) {
            // Could log e.getMessage() for debug purposes
            return false;
        }
    }

}
