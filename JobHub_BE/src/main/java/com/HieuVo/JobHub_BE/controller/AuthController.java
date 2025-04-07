package com.HieuVo.JobHub_BE.controller;


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
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuider;

    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManager, SecurityUtil securityUtil) {
        this.authenticationManagerBuider = authenticationManager;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = this.authenticationManagerBuider.getObject().authenticate(authenticationToken);
//       create a token
        String accessToken= this.securityUtil.createToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseLoginDTO res = new ResponseLoginDTO();
        res.setToken(accessToken);
        return ResponseEntity.ok().body(res);
    }
}
