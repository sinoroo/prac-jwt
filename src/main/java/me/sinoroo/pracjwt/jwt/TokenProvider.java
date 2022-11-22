package me.sinoroo.pracjwt.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.sinoroo.pracjwt.dto.TokenDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

   private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
   private static final String AUTHORITIES_KEY = "auth";
   private final String secret;
   private final long tokenValidityInMilliseconds;
   private final long refreshtokenValidityInMilliseconds;
   private Key key;


   public static enum JwtCode{
      DENIED,
      ACCESS,
      EXPIRED;
  }

   public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds, 
      @Value("${jwt.refreshtoken-validity-in-seconds}") long refreshtokenValidityInSeconds) {
      this.secret = secret;
      this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
      this.refreshtokenValidityInMilliseconds = refreshtokenValidityInSeconds * 1000;
   }

   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public TokenDto createToken(Authentication authentication) {

      String accessToken = createAccessToken( authentication);

      String refreshToken = createRefreshToken( authentication);

      return TokenDto.builder()
               .grantType("Bearer")
               .accessToken(accessToken)
               .refreshToken(refreshToken)
               .accessTokenExpireDate(this.tokenValidityInMilliseconds)
               .build();
   }


   public String createAccessToken(Authentication authentication) {
      String authorities = authentication.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority)
         .collect(Collectors.joining(","));

      long now = (new Date()).getTime();

      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SignatureAlgorithm.HS512)
         .setExpiration(new Date(now + this.tokenValidityInMilliseconds))
         .compact();
   }

   private String createRefreshToken(Authentication authentication) {
      String authorities = authentication.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority)
         .collect(Collectors.joining(","));

      long now = (new Date()).getTime();

      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SignatureAlgorithm.HS512)
         .setExpiration(new Date(now + this.refreshtokenValidityInMilliseconds))
         .compact();
   }

   public Authentication getAuthentication(String token) {
      Claims claims = Jwts
              .parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      Collection<? extends GrantedAuthority> authorities =
         Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);
   }

   public JwtCode validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
         return JwtCode.ACCESS;
      } catch (ExpiredJwtException e) {
         logger.info("만료된 JWT 토큰입니다.");
         return JwtCode.EXPIRED;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         logger.info("잘못된 JWT 서명입니다.");
      
      } catch (UnsupportedJwtException e) {
         logger.info("지원되지 않는 JWT 토큰입니다.");
      } catch (IllegalArgumentException e) {
         logger.info("JWT 토큰이 잘못되었습니다.");
      }
      return JwtCode.DENIED;
   }
}
