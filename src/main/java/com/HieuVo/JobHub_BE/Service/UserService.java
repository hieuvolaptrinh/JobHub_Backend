package com.HieuVo.JobHub_BE.Service;

import com.HieuVo.JobHub_BE.DTO.Response.*;
import com.HieuVo.JobHub_BE.DTO.Response.User.ResponseCreateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.User.ResponseUpdateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.User.ResponseUserDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.Model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
private final RoleService roleService;
    private final CompanyService companyService;

    public UserService(UserRepository userRepository, CompanyService companyService,
                       RoleService roleService)  {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    //    begin convert to DTO
    public ResponseUserDTO convertToResUserDTO(User user) {
        ResponseUserDTO res = new ResponseUserDTO();
        ResponseUserDTO.CompanyUser companyUser = new ResponseUserDTO.CompanyUser();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAge(user.getAge());
        res.setAddress(user.getAddress());
        res.setName(user.getName());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }
        return res;
    }

    public ResponseUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResponseUpdateUserDTO res = new ResponseUpdateUserDTO();
        ResponseUpdateUserDTO.CompanyUser companyUser = new ResponseUpdateUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
//
        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());

            res.setCompany(companyUser);
        }
        return res;
    }

    public ResponseCreateUserDTO convertToResCreateUserDTO(User user) {
        ResponseCreateUserDTO res = new ResponseCreateUserDTO();
        ResponseCreateUserDTO.CompanyUser companyUser = new ResponseCreateUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());

        if (user.getCompany() != null) {
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }

        return res;
    }

    //    end convert to DTO
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User handleCreateUser(User user) {
        System.out.printf("compa ny: %s", user.getCompany());
        if (user.getCompany() != null) {
            Company company = this.companyService.getCompanyById(user.getCompany().getId());
            if (company != null) {
                user.setCompany(company);
            } else {
                user.setCompany(null);
            }

        }
        if (user.getRole() != null) {
            Role role = this.roleService.getRoleById(user.getRole().getId());
            if (role != null) {
                user.setRole(role);
            } else {
                user.setRole(null);
            }
        }
        return this.userRepository.save(user);

    }

    public Void handleDeleteUser(long id) throws Exception {

        if (!this.userRepository.existsById(id)) {
            throw new Exception("User not found");
        }
        this.userRepository.deleteById(id);
        return null;
    }

    public User handleFetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public ResultPaginationDTO handleFetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());
        result.setMeta(meta);


        List<ResponseUserDTO> listResUserDto = pageUser.getContent()
                .stream().map(item -> new ResponseUserDTO(
                        item.getId(),
                        item.getEmail(),
                        item.getName(),
                        item.getGender(),
                        item.getAddress(),
                        item.getAge(),
                        item.getUpdatedAt(),
                        item.getCreatedAt(),
                        new ResponseUserDTO.CompanyUser(
                                item.getCompany() != null ? item.getCompany().getId() : 0,
                                item.getCompany() != null ? item.getCompany().getName() : null
                        )
                )).collect(Collectors.toList());

        result.setResult(listResUserDto);
        result.setResult(pageUser);

        return result;
    }

    public User handleUpdateUser(User user) {
        User currentUser = this.userRepository.getById(user.getId());
        if (currentUser != null) {
            currentUser.setAge(user.getAge());
            currentUser.setName(user.getName());
            currentUser.setAddress(user.getAddress());
            currentUser.setGender(user.getGender());

            if (user.getCompany() != null) {
                Company company = this.companyService.getCompanyById(user.getCompany().getId());
                if (company != null) {
                    currentUser.setCompany(company);
                } else {
                    currentUser.setCompany(null);
                }
            }
            if (user.getRole() != null) {
                Role role = this.roleService.getRoleById(user.getRole().getId());
                if (role != null) {
                    currentUser.setRole(role);
                } else {
                    currentUser.setRole(null);
                }
            }
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    //    update user token
    public void updateUserToken(String token, String email) {
        System.out.println("updateUserToken: " + token);
        System.out.println();
        User currenUser = this.userRepository.findByEmail(email);
        if (currenUser != null) {
            currenUser.setRefreshToken(token);
            this.userRepository.save(currenUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String refresh_token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(refresh_token, email);
    }
}
