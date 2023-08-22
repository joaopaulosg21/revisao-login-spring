package revisao.api.crudrevisao.configuration.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import revisao.api.crudrevisao.model.User;

import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exp}")
    private String exp;

    public String generate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date expiration = new Date(Long.parseLong(exp) + new Date().getTime());

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .setSubject(user.getId().toString())
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
