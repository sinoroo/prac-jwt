package me.sinoroo.pracjwt.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import me.sinoroo.pracjwt.entity.User;
import me.sinoroo.pracjwt.exception.CUserNotFoundException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindOneWithAuthoritiesByUsername() throws Exception {
        //given
        userRepository.save(User.builder()
            .username("Killme")
            .password("12345")
            .nickname("KillingM")
            .activated(true)
            .build());

        //when
        User user = userRepository.findOneWithAuthoritiesByUsername("Killme").orElseThrow(CUserNotFoundException::new);

        //then
        org.junit.jupiter.api.Assertions.assertNotNull(user);
        Assertions.assertThat(user.getNickname()).isEqualTo("killingM");
        Assertions.assertThat(user.getUsername()).isEqualTo("Killme");

    }
}
