package me.sinoroo.pracjwt.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.sinoroo.pracjwt.advice.exception.CDuplicatedNameException;
import me.sinoroo.pracjwt.advice.exception.CEmailLoginFailedException;
import me.sinoroo.pracjwt.advice.exception.CUserNotFoundException;
import me.sinoroo.pracjwt.model.response.CommonResult;
import me.sinoroo.pracjwt.service.ResponseService;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;


// 패키지 내 Controller 의 Exception처리
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //Http Response Code를 500으로 설정
    protected CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(
            Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e){
        return responseService.getFailResult(
                Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CDuplicatedNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult duplicatedNameException(HttpServletRequest request, CDuplicatedNameException e){
        return responseService.getFailResult(
                Integer.parseInt(getMessage("duplicatedUser.code")), getMessage("duplicatedUser.msg"));
    }

    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, CEmailLoginFailedException e){
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailLoginFailed.code")), getMessage("emailLoginFailed.msg"));
    }

    @ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult invalidKeyException(HttpServletRequest request, InvalidKeyException e){
        return responseService.getFailResult(
                Integer.parseInt(getMessage("invalidKey.code")), getMessage("invalidKey.msg"));
    }

    

    private String getMessage(String code){
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
