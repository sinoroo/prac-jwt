package me.sinoroo.pracjwt.service;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.sinoroo.pracjwt.dto.LoginDto;
import me.sinoroo.pracjwt.dto.TokenDto;
import me.sinoroo.pracjwt.dto.TokenRequestDto;
import me.sinoroo.pracjwt.entity.RefreshToken;
import me.sinoroo.pracjwt.entity.User;
import me.sinoroo.pracjwt.exception.CUserNotFoundException;
import me.sinoroo.pracjwt.jwt.TokenProvider;
import me.sinoroo.pracjwt.jwt.TokenProvider.JwtCode;
import me.sinoroo.pracjwt.repository.RefreshTokenJpaRepository;
import me.sinoroo.pracjwt.repository.UserRepository;

@Service
public class SecurityService {
    
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public SecurityService(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, RefreshTokenJpaRepository refreshTokenJpaRepository){
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    @Transactional
    public TokenDto login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findOneWithAuthoritiesByUsername(authentication.getName()).orElse(null);

        TokenDto jwt = tokenProvider.createToken(authentication);

        // 로그인 시 발급된 Refresh Token을 UserId 와 함께 저장
        RefreshToken refreshToken = RefreshToken.builder().userId(user.getUserId()).token(jwt.getRefreshToken()).build();
        refreshTokenJpaRepository.save(refreshToken);
        return jwt;
    }

    
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        if(tokenProvider.validateToken(tokenRequestDto.getRefreshToken()) == JwtCode.EXPIRED){
            throw new RuntimeException("refresh token expired");
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        User user = userRepository.findOneWithAuthoritiesByUsername(authentication.getName()).orElse(null);
        RefreshToken refreshToken = refreshTokenJpaRepository.findByUserId(user.getUserId()).orElse(null);

        if( !refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new RuntimeException("refresh token no matched");

        TokenDto newJwt = tokenProvider.createToken(authentication);
        RefreshToken updatedRefreshToken = refreshToken.updateToken(newJwt.getRefreshToken());
        refreshTokenJpaRepository.save(updatedRefreshToken);

        return newJwt;
    }

    @Transactional
    public String reissue(String refresh){
        
        Authentication authentication = tokenProvider.getAuthentication(refresh);

        User user = userRepository.findOneWithAuthoritiesByUsername(authentication.getName()).orElse(null);
        RefreshToken refreshToken = refreshTokenJpaRepository.findByUserId(user.getUserId()).orElse(null);

        if( refreshToken.getToken().equals(refresh))
        {
            //새로운 토큰 생성
            TokenDto newJwt = tokenProvider.createToken(authentication);
            RefreshToken updatedRefreshToken = refreshToken.updateToken(newJwt.getRefreshToken());
            refreshTokenJpaRepository.save(updatedRefreshToken);
            return newJwt.getRefreshToken();
        }
        else {
            return null;
        }

        
    }
}
