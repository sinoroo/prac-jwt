package me.sinoroo.pracjwt.repository;

import me.sinoroo.pracjwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
   
    Optional<RefreshToken> findByUserId(Long userId);
}
