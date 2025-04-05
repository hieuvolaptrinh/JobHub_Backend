package com.HieuVo.Employee_Recruitment_Management.controller;


import com.HieuVo.Employee_Recruitment_Management.Model.DTO.LoginDTO;
import com.HieuVo.Employee_Recruitment_Management.Util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuider;

    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManager, SecurityUtil securityUtil) {
        this.authenticationManagerBuider = authenticationManager;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = this.authenticationManagerBuider.getObject().authenticate(authenticationToken);
//       create a token
        this.securityUtil.createToken(authentication);
        return ResponseEntity.ok().body(loginDTO);
    }
}
