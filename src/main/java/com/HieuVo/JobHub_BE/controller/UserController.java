package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.DTO.ResultPaginationDTO;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.HieuVo.JobHub_BE.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.handleCreateUser(user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchtUserById(id));
    }

    @GetMapping()
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            @RequestParam(value = "current", defaultValue = "1") String current,
            @RequestParam(value = "pageSize", defaultValue = "5") String pageSize) {
        int currentPage = Integer.parseInt(current);
        int pageSizeInt = Integer.parseInt(pageSize);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSizeInt);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec));
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(user));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if (id > 100) {
            throw new IdInvalidException("id khong ton taij ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleDeleteUser(id));
    }

}
