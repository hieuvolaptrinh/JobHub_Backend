package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.DTO.Response.ResponseCreateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResponseUpdateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResponseUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.HieuVo.JobHub_BE.Service.UserService;

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
    @ApiMessage("create a UserLogin success")
    public ResponseEntity<ResponseCreateUserDTO> createUser(@RequestBody User user) throws Exception {
        boolean isEmailExist = this.userService.checkEmailExist(user.getEmail());
        if (isEmailExist) {
            throw new Exception("Email " + user.getEmail() + " da ton tai, vui long su dung email khac");
        }
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
    }


    @GetMapping("/{id}")
    @ApiMessage("Get UserLogin by id success")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable("id") long id) throws IdInvalidException {
        User getUser = this.userService.handleFecthUserById(id);
        if (getUser == null) {
            throw new IdInvalidException("Id " + id + " khong ton tai");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(getUser));
    }

    @GetMapping()
    @ApiMessage("Get all UserLogin success")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable // page,size,sort
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFetchAllUser(spec, pageable));
    }

    @PutMapping()
    @ApiMessage("Update UserLogin success")
    public ResponseEntity<ResponseUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        User updateUser = this.userService.handleUpdateUser(user);
        if (updateUser == null) {
            throw new IdInvalidException("Id " + user.getId() + " khong ton tai");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updateUser));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete UserLogin success")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleDeleteUser(id));
    }

}
