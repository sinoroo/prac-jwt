package me.sinoroo.pracjwt.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import me.sinoroo.pracjwt.service.SecurityService;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    private SecurityService securityService;
    public JwtSecurityConfig(TokenProvider tokenProvider, SecurityService securityService) {
        this.tokenProvider = tokenProvider;
        this.securityService = securityService;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
            new JwtFilter(tokenProvider, securityService),
            UsernamePasswordAuthenticationFilter.class
        );
    }
}
