package me.sinoroo.pracjwt.controller;

import me.sinoroo.pracjwt.dto.UserDto;
import me.sinoroo.pracjwt.entity.User;
import me.sinoroo.pracjwt.model.response.SingleResult;
import me.sinoroo.pracjwt.service.ResponseService;
import me.sinoroo.pracjwt.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    public UserController(UserService userService, ResponseService responseService){
        this.userService = userService;
        this.responseService = responseService;
    }

    @PostMapping("/signup")
    public SingleResult<User> signup(@Valid @RequestBody UserDto userDto){
        //return ResponseEntity.ok(userService.signup(userDto));
        return responseService.getSingleResult(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
