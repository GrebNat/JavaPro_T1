package org.example.service;

import org.example.dto.UserLimitResponseDto;
import org.example.entity.UserLimitEntity;
import org.example.exception.LimitExceededException;
import org.example.exception.UserLimitAlreadyExistsException;
import org.example.repository.UserLimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

@Service
public class LimitService {

    @Value("${limits.default-limit}")
    private BigDecimal defaultLimit;
    @Value("${limits.default-reserve-limit}")
    private BigDecimal defaultReserveLimit;

    private final UserLimitRepository limitRepository;

    public LimitService(UserLimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public void debitUserLimit(Long userId, BigDecimal limit) {
        BigDecimal currentLimit = getLimitByUserId(userId).currentLimit();
        BigDecimal newLimit = currentLimit.subtract(limit);

        if (currentLimit.compareTo(newLimit) < 0) {
            throw new LimitExceededException(format("Лимит пользователя id=%s %s. Превышен", userId, limit.toString()));
        }

        limitRepository.updateCurrentLimit(userId, newLimit);
        limitRepository.updateReserved(userId, defaultReserveLimit);
    }

    public void reserve(Long userId, BigDecimal reserve) {
        BigDecimal currentLimit = getLimitByUserId(userId).currentLimit();

        if (currentLimit.compareTo(reserve) < 0) {
            throw new LimitExceededException(format("Лимит пользователя id=%s %s. Превышен", userId, reserve.toString()));
        }

        limitRepository.updateReserved(userId, reserve);
    }

    public void confirm(Long userId) {
        BigDecimal currentLimit = getLimitByUserId(userId).currentLimit();
        BigDecimal currentReserve = getLimitByUserId(userId).reserved();

        if (currentLimit.compareTo(currentReserve) < 0) {
            throw new LimitExceededException(format("Лимит пользователя id=%s %s. Превышен резервом", userId, currentReserve.toString()));
        }

        BigDecimal newLimit = currentLimit.subtract(currentReserve);
        limitRepository.updateCurrentLimit(userId, newLimit);
        limitRepository.updateReserved(userId, defaultReserveLimit);
    }

    public void cancelReservation(Long userId) {
        limitRepository.updateReserved(userId, defaultReserveLimit);
    }

    public UserLimitResponseDto createNewUserLimit(Long userId) {
        if (limitRepository.getUserLimitByUserId(userId) != null)
            throw new UserLimitAlreadyExistsException(format("Для userId=%s уже существует лимит", userId));

        UserLimitEntity userLimitEntity = new UserLimitEntity();
        userLimitEntity.setUserId(userId);
        userLimitEntity.setCurrentLimit(defaultLimit);
        userLimitEntity.setReserved(defaultReserveLimit);

        UserLimitEntity resultEntity = limitRepository.save(userLimitEntity);

        return new UserLimitResponseDto(
                resultEntity.getUserId(),
                resultEntity.getCurrentLimit(),
                resultEntity.getReserved()
        );
    }

    public UserLimitResponseDto getLimitByUserId(Long userId) {
        if (limitRepository.getUserLimitByUserId(userId) == null)
            createNewUserLimit(userId);

        UserLimitEntity userLimitEntity = limitRepository.getUserLimitByUserId(userId);

        return new UserLimitResponseDto(
                userLimitEntity.getUserId(),
                userLimitEntity.getCurrentLimit(),
                userLimitEntity.getReserved()
        );
    }

    public void updateUserLimitToDefault(Long userId) {
        limitRepository.updateCurrentLimit(userId, defaultLimit);
    }

    public void updateReservedLimitToDefault(Long userId) {
        limitRepository.updateReserved(userId, defaultReserveLimit);
    }

    public List<UserLimitResponseDto> getAllLimits() {
        return limitRepository
                .findAll()
                .stream()
                .map(x -> new UserLimitResponseDto(
                        x.getUserId(),
                        x.getCurrentLimit(),
                        x.getReserved()
                ))
                .toList();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetAllLimits() {
        getAllLimits().forEach(x -> {
            updateUserLimitToDefault(x.userId());
            updateReservedLimitToDefault(x.userId());
        });
    }
}
