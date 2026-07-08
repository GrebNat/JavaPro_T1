package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.LimitDebitDto;
import org.example.dto.ReservedDto;
import org.example.dto.UserLimitResponseDto;
import org.example.service.LimitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/limit")
public class LimitController {

    private final LimitService limitService;

    @PostMapping("/debit/{userId}")
    public void debitUserLimit(@PathVariable Long userId, @RequestBody LimitDebitDto limitUpdateDto) {
        limitService.debitUserLimit(userId, limitUpdateDto.limit());
    }

    @PostMapping("/reserve/{userId}")
    public void reserve(@PathVariable Long userId, @RequestBody ReservedDto reservedDto) {
        limitService.reserve(userId, reservedDto.reserve());
    }

    @GetMapping("/confirm/{userId}")
    public void confirmUserLimit(@PathVariable Long userId) {
        limitService.confirm(userId);
    }

    @GetMapping("/cancel/{userId}")
    public void cancelReservationUserLimit(@PathVariable Long userId) {
        limitService.cancelReservation(userId);
    }

    @GetMapping("/{userId}")
    public UserLimitResponseDto getLimitByUserId(@PathVariable Long userId) {
        return limitService.getLimitByUserId(userId);
    }

    @GetMapping("/all")
    public List<UserLimitResponseDto> getAllLimits() {
        return limitService.getAllLimits();
    }
}
