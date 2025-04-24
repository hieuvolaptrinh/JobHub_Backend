package com.HieuVo.JobHub_BE.config;


import java.util.List;

import com.HieuVo.JobHub_BE.Model.Permission;
import com.HieuVo.JobHub_BE.Model.Role;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Service.UserService;
import com.HieuVo.JobHub_BE.Util.Error.PermissionException;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        // check permission
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        if (email != null && !email.isEmpty()) {
            User user = this.userService.findByEmail(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    List<Permission> permissions = role.getPermissions();
                    boolean isAllow = permissions.stream()
                            .anyMatch(item -> item.getApiPath().equals(path) && item.getMethod().equals(httpMethod));
                    System.out.println("Allow: " + isAllow);
                    if (isAllow == false) {
                        throw new PermissionException("Access denied");
                    }
                } else {
                    throw new PermissionException("Access denied");
                }

            }
        }
        return true;
    }
}
