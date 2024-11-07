package springdingdong.pss.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class TokenProvider {
    private final String secretKey;
    private final SecretKey key;

    //JWT 역직렬화를 위한 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey=secretKey;
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }
    //토큰 제작 메소드
    public String createAccessToken(String username) {
        return Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .subject(username)
                .issuer("springdingdong")
                .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .expiration(Date.from(Instant.now().plus(3L, ChronoUnit.HOURS)))
                .compact();
    }

    //토큰 검증 메소드
    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token)
                .getPayload()
                .getSubject();
    }
    //토큰의 payload 꺼내는 메소드
    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    //JWT 복호화, payload 안에 sub 반환
    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }
}
