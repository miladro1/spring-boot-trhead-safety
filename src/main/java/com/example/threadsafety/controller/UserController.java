package com.example.threadsafety.controller;

import com.example.threadsafety.DTO.UserDto;
import com.example.threadsafety.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public int addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/create-sync")
    public int addUserSync(@RequestBody UserDto userDto) {
        return userService.addUserSync(userDto);
    }
}
