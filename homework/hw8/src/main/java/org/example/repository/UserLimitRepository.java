package org.example.repository;

import org.example.entity.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {

    UserLimit getUserLimitById(Long id);

    UserLimit getUserLimitByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_limit SET current_limit = :newAmount WHERE id = :userId", nativeQuery = true)
    int updateCurrentLimit(Long userId, BigDecimal newAmount);
}
