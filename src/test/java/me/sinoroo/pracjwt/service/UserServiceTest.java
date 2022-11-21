package me.sinoroo.pracjwt.service;

import java.util.Collections;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import me.sinoroo.pracjwt.dto.AuthorityDto;
import me.sinoroo.pracjwt.dto.UserDto;
import me.sinoroo.pracjwt.entity.Authority;
import me.sinoroo.pracjwt.entity.User;
import me.sinoroo.pracjwt.exception.CDuplicatedNameException;
import me.sinoroo.pracjwt.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    UserService userService;

    /*
    @BeforeEach
    public void beforeEach(){
        userService = new UserService(null, null);
    }

    @AfterEach
    public void afterEach(){

    }
    */

    @Test
    void testSignup() throws Exception {
        

    }
}
