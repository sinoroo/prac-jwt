package me.sinoroo.pracjwt.entity;

import java.time.LocalDateTime;
import java.util.List;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import me.sinoroo.pracjwt.repository.UserRepository;

@SpringBootTest
@Transactional
public class BaseTimeEntityTest {

    @Autowired UserRepository userRepository;
    @Test
    void RegisterBaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 11, 21, 10, 30, 0);
        userRepository.save(User.builder().username("killme").nickname("killingD").build());

        User testUser = User.builder()
                    .username("Killme")
                    .password("12345")
                    .nickname("KillingM")
                    .activated(true)
                    .build();
        userRepository.save(testUser);
        
        //when
        List<User> users = userRepository.findAll();

        //then
        User user = users.get(2);
        
        Assertions.assertThat(user.getCreatedDate()).isAfter(now);
        Assertions.assertThat(user.getModifiedDate()).isAfter(now);
    }

}
