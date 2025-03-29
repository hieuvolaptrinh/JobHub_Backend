package com.HieuVo.Employee_Recruitment_Management.controller;

import com.HieuVo.Employee_Recruitment_Management.domain.User;

import com.HieuVo.Employee_Recruitment_Management.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping()
    public User createUser(@RequestBody User user) {
        return this.userService.handleCreateUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "User with id " + id + " has been deleted";
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")long id){
        return this.userService.fetchtUserById(id);
    }

    @GetMapping()
    public List<User> getAllUser(){
        return this.userService.fetchAllUser();
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {

    return this.userService.handleUpdateUser(user);
    }

}
