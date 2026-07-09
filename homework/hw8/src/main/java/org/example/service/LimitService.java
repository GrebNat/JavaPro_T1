package org.example.service;

import lombok.val;
import org.example.dto.ReservedDto;
import org.example.dto.UserLimitResponseDto;
import org.example.entity.UserLimitEntity;
import org.example.exception.LimitExceededException;
import org.example.exception.ReserveExceededException;
import org.example.exception.UserLimitAlreadyExistsException;
import org.example.repository.UserLimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

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

        if (newLimit.compareTo(ZERO) < 0) {
            throw new LimitExceededException(userId, limit);
        }

        limitRepository.updateCurrentLimit(userId, newLimit);
        limitRepository.updateReserved(userId, defaultReserveLimit);
    }

    public void reserve(Long userId, BigDecimal reserve) {
        UserLimitResponseDto userLimit = getLimitByUserId(userId);

        BigDecimal currentLimit = userLimit.currentLimit();
        BigDecimal currentReserve = userLimit.reserved();
        BigDecimal newReserve = currentReserve.add(reserve);

        if (currentLimit.compareTo(newReserve) < 0) {
            throw new LimitExceededException(userId, currentLimit);
        }

        limitRepository.updateReserved(userId, newReserve);
    }

    public void confirm(Long userId, ReservedDto reservedDto) {
        UserLimitResponseDto userLimit = getLimitByUserId(userId);

        BigDecimal currentLimit = userLimit.currentLimit();
        BigDecimal currentReserve = userLimit.reserved();
        BigDecimal reserveToSubtract = reservedDto.reserve();

        if (currentReserve.compareTo(reserveToSubtract) < 0) {
            throw new LimitExceededException(userId, currentReserve);
        }

        if (currentLimit.compareTo(reserveToSubtract) < 0) {
            throw new LimitExceededException(userId, currentReserve);
        }

        BigDecimal newLimit = currentLimit.subtract(reserveToSubtract);
        BigDecimal newReserve = currentReserve.subtract(reserveToSubtract);

        limitRepository.updateCurrentLimit(userId, newLimit);
        limitRepository.updateReserved(userId, newReserve);
    }

    public void cancelReservation(Long userId, ReservedDto reservedDto) {
        BigDecimal currentReserve = getLimitByUserId(userId).reserved();

        if (currentReserve.compareTo(reservedDto.reserve()) < 0) {
            throw new ReserveExceededException(userId, currentReserve);
        }

        BigDecimal newReserve = currentReserve.subtract(reservedDto.reserve());

        limitRepository.updateReserved(userId, newReserve);
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
        limitRepository.updateAllReservedAndLimitToDefault(defaultReserveLimit, defaultLimit);
    }
}
