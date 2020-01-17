import com.sun.config.AuthorizationConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JWTTest {

    @Test
    public void createNewToken() {
        Key jwtKey = createJwtKey();

        Claims claims = Jwts.claims();
        claims.put(AuthorizationConst.USER_ID, -1);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("test")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(jwtKey)
                .compact();

        System.out.println(token);

        Claims body = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
        System.out.println(body.getExpiration());
    }


    @Test
    public void parseToken() {
        Key jwtKey = createJwtKey();
        String token ="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6Iuezu-e7n-euoeeQhuWRmCIsImlhdCI6MTU3NzQzNDE1NSwiZXhwIjoxNTc3NDcwMTU1fQ.BoYcAS1njjdd9pecQuE0AB3GNakjIZ1PJT1XvfiyMj4";
        Claims body = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
        System.out.println(body.getExpiration());
    }

    private Key createJwtKey() {
        Key key;
        Assert.hasLength("JDuwadvf89sEWdQfx", "jwtSecret 不能为空");
        byte[] bytes = "JDuwadvf89sEWdQfx".getBytes(StandardCharsets.UTF_8);

        byte[] byteKey = new byte[32];
        if (bytes.length > 32) {
            System.arraycopy(bytes, 0, byteKey, 0, 32);
        } else {
            System.arraycopy(bytes, 0, byteKey, 0, bytes.length);
            for (int i = bytes.length; i < 32; i++) {
                byteKey[i] = (byte) (i % 32);
            }
        }
        key = Keys.hmacShaKeyFor(byteKey);
        return key;
    }
}
