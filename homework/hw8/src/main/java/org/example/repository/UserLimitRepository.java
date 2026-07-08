package org.example.repository;

import org.example.entity.UserLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface UserLimitRepository extends JpaRepository<UserLimitEntity, Long> {

    UserLimitEntity getUserLimitByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_limit SET current_limit = :newAmount WHERE id = :userId", nativeQuery = true)
    int updateCurrentLimit(Long userId, BigDecimal newAmount);


    @Modifying
    @Transactional
    @Query(value = "UPDATE user_limit SET reserved = :newAmount WHERE id = :userId", nativeQuery = true)
    int updateReserved(Long userId, BigDecimal newAmount);
}
