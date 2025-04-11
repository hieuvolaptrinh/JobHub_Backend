package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Service.UserService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HieuVo.JobHub_BE.DTO.LoginDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResponseLoginDTO;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuider;

    private final SecurityUtil securityUtil;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManager, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuider = authenticationManager;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }


    @PostMapping("/login")
    @ApiMessage("Login success")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
//        Nap username va password vao authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
//        xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = this.authenticationManagerBuider.getObject().authenticate(authenticationToken);
//       create a token
        String accessToken = this.securityUtil.createToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        User currentUserDB = this.userService.findByEmail(loginDTO.getUsername());
        ResponseLoginDTO res = new ResponseLoginDTO();
        ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(currentUserDB.getId(),currentUserDB.getEmail(),currentUserDB.getName());
        res.setUser(userLogin);
//        query user lại
        userLogin.setId(1L);


        res.setAccessToken(accessToken);
        return ResponseEntity.ok().body(res);
    }
}
