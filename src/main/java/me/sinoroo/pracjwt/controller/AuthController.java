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
import me.sinoroo.pracjwt.service.SecurityService;
import me.sinoroo.pracjwt.dto.LoginDto;
import me.sinoroo.pracjwt.dto.TokenDto;
import me.sinoroo.pracjwt.dto.TokenRequestDto;
import me.sinoroo.pracjwt.jwt.JwtFilter;
import me.sinoroo.pracjwt.jwt.TokenProvider;
import me.sinoroo.pracjwt.model.response.SingleResult;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final SecurityService securityService;
    private final ResponseService responseService;
    
    public AuthController(SecurityService securityService, ResponseService responseService){
        this.securityService = securityService;
        this.responseService = responseService;
    }


    @PostMapping("/signin")
    public SingleResult<TokenDto> login(@Valid @RequestBody LoginDto loginDto){
        return responseService.getSingleResult(securityService.login(loginDto));
    }

    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto){
        return responseService.getSingleResult(securityService.reissue(tokenRequestDto));
    }
}
