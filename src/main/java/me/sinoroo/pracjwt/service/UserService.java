package me.sinoroo.pracjwt.service;

import java.lang.StackWalker.Option;
import java.util.Collections;
import java.util.Optional;

import me.sinoroo.pracjwt.entity.User;
import me.sinoroo.pracjwt.exception.CUserNotFoundException;
import me.sinoroo.pracjwt.exception.NotFoundMemberException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.sinoroo.pracjwt.dto.UserDto;
import me.sinoroo.pracjwt.entity.Authority;
import me.sinoroo.pracjwt.repository.UserRepository;
import me.sinoroo.pracjwt.util.SecurityUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto){
        if( userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                            .authorityName("ROLE_USER")
                            .build();
        
        User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .nickname(userDto.getNickname())
                    .authorities(Collections.singleton(authority))
                    .activated(true)
                    .build();
        
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new CUserNotFoundException("Member not found")
                        ));
    }
}
