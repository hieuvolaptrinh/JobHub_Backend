package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Service.UserService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.HieuVo.JobHub_BE.DTO.LoginDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResponseLoginDTO;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuider;

    private final SecurityUtil securityUtil;
    private final UserService userService;
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManager, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuider = authenticationManager;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }


    @PostMapping("/auth/login")
    @ApiMessage("Login success")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
//        Nap username va password vao authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername()
                        , loginDTO.getPassword());
//        xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = this.authenticationManagerBuider.getObject().authenticate(authenticationToken);
//        set thông tin đăng nhập vào context để sau này xài
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //       create a token
        User currentUserDB = this.userService.findByEmail(loginDTO.getUsername());

        ResponseLoginDTO res = new ResponseLoginDTO();
        ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(
                currentUserDB.getId(),
                currentUserDB.getEmail(),
                currentUserDB.getName());
        res.setUser(userLogin);

//        create access token
        String accessToken = this.securityUtil.createAccessToken(authentication, userLogin);
        res.setAccessToken(accessToken);
//        create refresh token
        String refreshToken = this.securityUtil.createRefreshToken(loginDTO.getUsername(), res);


        this.userService.updateUserToken(loginDTO.getUsername(), refreshToken);

//set cookies
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
//                .domain() // website
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(res);
    }

    @GetMapping("/auth/account")
    @ApiMessage("Get account success")
    public ResponseEntity<ResponseLoginDTO.UserLogin> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin()
                .isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUserDB = this.userService.findByEmail(email);
        ResponseLoginDTO res = new ResponseLoginDTO();
        ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(currentUserDB.getId(), currentUserDB.getEmail(), currentUserDB.getName());
        res.setUser(userLogin);
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/auth/refresh")
    @ApiMessage("get user by refresh token")
    public ResponseEntity<String> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "") String refresh_token) {
//        check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);

        String email = decodedToken.getSubject(); //Lấy ở Subject
        return ResponseEntity.ok().body(email);
    }
}
