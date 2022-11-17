package me.sinoroo.pracjwt.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.sinoroo.pracjwt.model.response.CommonResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    
    @GetMapping("/entrypoint")
    public CommonResult entrypointException(){
        throw new CAuthenticationEntrypointException();
    }

    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException(){
        throw new AccessDeniedException("");
    }
}
