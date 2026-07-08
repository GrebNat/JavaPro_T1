package org.example.controller;

import org.example.dto.LimitUpdateDto;
import org.example.service.LimitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/v1/limit")
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping("/{userId}")
    public BigDecimal getLimitByUserId(@PathVariable Long userId){
        return limitService.getLimitByUserId(userId);
    }

    @PatchMapping("/{userId}")
    public void updateUserLimit(@PathVariable Long userId, @RequestBody LimitUpdateDto limitUpdateDto){
        limitService.updateUserLimit(userId, limitUpdateDto.limit());
    }

    @PatchMapping("/{userId}/default")
    public void updateUserLimit(@PathVariable Long userId){
        limitService.updateUserLimitToDefault(userId);
    }

    @PostMapping("/new")
    public void addNewUserLimit(@RequestBody LimitUpdateDto limitDto){
        limitService.addNewUserLimit(limitDto);
    }
}
