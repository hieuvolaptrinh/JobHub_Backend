package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.DTO.Response.User.ResponseCreateUserDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Service.UserService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.HieuVo.JobHub_BE.repository.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.HieuVo.JobHub_BE.DTO.Request.RequestLoginDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResponseLoginDTO;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

        private final AuthenticationManagerBuilder authenticationManagerBuider;

        private final SecurityUtil securityUtil;
        private final UserService userService;
        private final CompanyRepository companyRepository;
        private final PasswordEncoder passwordEncoder;

        @Value("${jwt.refresh-token-validity-in-seconds}")
        private long refreshTokenExpiration;

        public AuthController(AuthenticationManagerBuilder authenticationManager, SecurityUtil securityUtil,
                              UserService userService, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
                this.authenticationManagerBuider = authenticationManager;
                this.userService = userService;
                this.securityUtil = securityUtil;
                this.companyRepository = companyRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/auth/login")
        @ApiMessage("Login success")
        public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid RequestLoginDTO loginDTO) {
                // Nap username va password vao authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.getUsername(), loginDTO.getPassword());
                // xác thực người dùng => cần viết hàm loadUserByUsername
                Authentication authentication = this.authenticationManagerBuider.getObject()
                                .authenticate(authenticationToken);
                // set thông tin đăng nhập vào context để sau này xài
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // create a token
                User currentUserDB = this.userService.findByEmail(loginDTO.getUsername());
                ResponseLoginDTO res = new ResponseLoginDTO();

                // Create role DTO
                ResponseLoginDTO.RoleDTO roleDTO = new ResponseLoginDTO.RoleDTO();
                roleDTO.setId(currentUserDB.getRole().getId());
                roleDTO.setName(currentUserDB.getRole().getName());
                roleDTO.setDescription(currentUserDB.getRole().getDescription());

                // Create permission DTOs
                List<ResponseLoginDTO.PermissionDTO> permissionDTOs = currentUserDB.getRole().getPermissions().stream()
                                .map(permission -> {
                                        ResponseLoginDTO.PermissionDTO permissionDTO = new ResponseLoginDTO.PermissionDTO();
                                        permissionDTO.setId(permission.getId());
                                        permissionDTO.setName(permission.getName());
                                        permissionDTO.setApiPath(permission.getApiPath());
                                        permissionDTO.setMethod(permission.getMethod());
                                        permissionDTO.setModule(permission.getModule());
                                        return permissionDTO;
                                })
                                .toList();

                roleDTO.setPermissions(permissionDTOs);

                // Create user login DTO
                ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin();
                userLogin.setId(currentUserDB.getId());
                userLogin.setEmail(currentUserDB.getEmail());
                userLogin.setName(currentUserDB.getName());
                userLogin.setRole(roleDTO);

                res.setUser(userLogin);
                // create access token
                String accessToken = this.securityUtil.createAccessToken(authentication.getName(), res);
                res.setAccessToken(accessToken);
                // create refresh token
                String refreshToken = this.securityUtil.createRefreshToken(loginDTO.getUsername(), res);

                System.out.println("Refresh token: " + refreshToken);
                this.userService.updateUserToken(refreshToken, loginDTO.getUsername());

                // set cookies
                ResponseCookie resCookies = ResponseCookie.from("refresh_token", refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(refreshTokenExpiration)
                                // .domain() // website
                                .build();
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(res);
        }

        @GetMapping("/auth/account")
        @ApiMessage("Get account success")
        public ResponseEntity<ResponseLoginDTO.UserGetAccount> getAccount() {
                String email = SecurityUtil.getCurrentUserLogin()
                                .isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
                User currentUser = this.userService.findByEmail(email);
                ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin();
                ResponseLoginDTO.UserGetAccount userGetAccount = new ResponseLoginDTO.UserGetAccount();

                // Create role DTO
                ResponseLoginDTO.RoleDTO roleDTO = new ResponseLoginDTO.RoleDTO();
                roleDTO.setId(currentUser.getRole().getId());
                roleDTO.setName(currentUser.getRole().getName());
                roleDTO.setDescription(currentUser.getRole().getDescription());

                if (currentUser != null) {
                        userLogin.setEmail(currentUser.getEmail());
                        userLogin.setId(currentUser.getId());
                        userLogin.setName(currentUser.getName());
                        userLogin.setRole(roleDTO);
                        userGetAccount.setUser(userLogin);
                }
                return ResponseEntity.ok().body(userGetAccount);
        }

        @GetMapping("/auth/refresh")
        @ApiMessage("get user by refresh token")
        public ResponseEntity<ResponseLoginDTO> getRefreshToken(
                        @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token)
                        throws Exception {
                if (refresh_token.equals("abc")) {
                        throw new Exception("Không có cookies");

                }
                // check valid
                Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
                String email = decodedToken.getSubject(); // Lấy ở Subject
                // check user by token + email
                User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
                if (currentUser == null) {
                        throw new IdInvalidException("Refresh token is invalid");
                }
                // issue new token/ set refresh token as cookies
                User currentUserDB = this.userService.findByEmail(email);
                ResponseLoginDTO res = new ResponseLoginDTO();

                // Create role DTO
                ResponseLoginDTO.RoleDTO roleDTO = new ResponseLoginDTO.RoleDTO();
                roleDTO.setId(currentUserDB.getRole().getId());
                roleDTO.setName(currentUserDB.getRole().getName());
                roleDTO.setDescription(currentUserDB.getRole().getDescription());

                // Create permission DTOs
                List<ResponseLoginDTO.PermissionDTO> permissionDTOs = currentUserDB.getRole().getPermissions().stream()
                                .map(permission -> {
                                        ResponseLoginDTO.PermissionDTO permissionDTO = new ResponseLoginDTO.PermissionDTO();
                                        permissionDTO.setId(permission.getId());
                                        permissionDTO.setName(permission.getName());
                                        permissionDTO.setApiPath(permission.getApiPath());
                                        permissionDTO.setMethod(permission.getMethod());
                                        permissionDTO.setModule(permission.getModule());
                                        return permissionDTO;
                                })
                                .toList();

                roleDTO.setPermissions(permissionDTOs);

                // Create user login DTO
                ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin();
                userLogin.setId(currentUserDB.getId());
                userLogin.setEmail(currentUserDB.getEmail());
                userLogin.setName(currentUserDB.getName());
                userLogin.setRole(roleDTO);

                res.setUser(userLogin);
                // create access token
                String accessToken = this.securityUtil.createAccessToken(email, res);
                res.setAccessToken(accessToken);
                // create refresh token
                String new_refreshToken = this.securityUtil.createRefreshToken(email, res);
                this.userService.updateUserToken(new_refreshToken, email);
                // set cookies
                ResponseCookie resCookies = ResponseCookie.from("refresh_token", new_refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(refreshTokenExpiration)

                                .build();
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(res);
        }

        // logout
        @PostMapping("/auth/logout")
        @ApiMessage("Logout success")
        public ResponseEntity<Void> logOut() throws Exception {
                String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get()
                                : "";
                if (email.isEmpty()) {
                        throw new Exception("Token không hợp lê");
                }
                this.userService.updateUserToken(null, email);

                ResponseCookie deleteCookie = ResponseCookie
                                .from("refresh_token", null)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(0)
                                .build();

                return ResponseEntity
                                .ok()
                                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                                .build();
        }

        @PostMapping("/auth/register")
        @ApiMessage("create a UserLogin success")
        public ResponseEntity<ResponseCreateUserDTO> createUser(@RequestBody User user) throws Exception {
                boolean isEmailExist = this.userService.checkEmailExist(user.getEmail());
                if (isEmailExist) {
                        throw new Exception("Email " + user.getEmail() + " da ton tai, vui long su dung email khac");
                }
                System.out.println("User: " + user.toString());
                Company company = companyRepository.findById(user.getCompany().getId())
                        .orElseThrow(() -> new Exception("Company not found"));
                String hashPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(hashPassword);
                User newUser = this.userService.handleCreateUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
        }
}
