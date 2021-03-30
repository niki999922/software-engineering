package com.kochetkov.stonks.controller;

import com.kochetkov.stonks.model.Stock;
import com.kochetkov.stonks.model.User;
import com.kochetkov.stonks.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@RequestParam String login) {
        return userService.register(login);
    }

    @GetMapping("/{login}")
    public User authorize(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @GetMapping("/money/{login}")
    public Long getMoney(@PathVariable String login) {
        return userService.getMoney(login);
    }

    @PutMapping("/buy/{login}")
    public void buy(@PathVariable String login, @RequestParam String company, @RequestParam @Range(max = 100L) Long count) {
        userService.buy(login, company, count);
    }

    @PutMapping("/sell/{login}")
    public void sell(@PathVariable String login, @RequestParam String company, @RequestParam @Range(max = 100L) Long count) {
        userService.sell(login, company, count);
    }

    @PutMapping("/deposit/{login}")
    public void deposit(@PathVariable String login, @RequestParam @Range(max = 10000L) Long value) {
        userService.deposit(login, value);
    }

    @GetMapping("/portfolio/{login}")
    public List<Stock> getPortfolio(@PathVariable String login) {
        return userService.getPortfolio(login);
    }

}
