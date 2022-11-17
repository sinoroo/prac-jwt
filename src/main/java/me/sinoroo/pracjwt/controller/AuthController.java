package me.sinoroo.pracjwt.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.sinoroo.pracjwt.service.ResponseService;

import me.sinoroo.pracjwt.dto.LoginDto;
import me.sinoroo.pracjwt.dto.TokenDto;
import me.sinoroo.pracjwt.jwt.JwtFilter;
import me.sinoroo.pracjwt.jwt.TokenProvider;
import me.sinoroo.pracjwt.model.response.SingleResult;

@RestController
@RequestMapping("/api")
public class AuthController {


    private final TokenProvider tokenProvider;
    private final ResponseService responseService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, ResponseService responseService, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.tokenProvider = tokenProvider;
        this.responseService = responseService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    @PostMapping("/signin")
    public SingleResult<TokenDto> login(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        //return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        return responseService.getSingleResult(new TokenDto(jwt));
    }
}
