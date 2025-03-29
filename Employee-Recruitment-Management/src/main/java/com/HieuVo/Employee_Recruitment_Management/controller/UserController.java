package com.HieuVo.Employee_Recruitment_Management.controller;

import com.HieuVo.Employee_Recruitment_Management.domain.User;
import com.HieuVo.Employee_Recruitment_Management.repository.UserRepository;
import com.HieuVo.Employee_Recruitment_Management.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return this.userService.handleCreateUser(user);
         }
}
