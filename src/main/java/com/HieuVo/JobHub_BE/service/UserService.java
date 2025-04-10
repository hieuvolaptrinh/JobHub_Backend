package com.HieuVo.JobHub_BE.service;

import com.HieuVo.JobHub_BE.DTO.Meta;
import com.HieuVo.JobHub_BE.DTO.ResultPaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {

        return this.userRepository.save(user);
    }

    public String handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public User fetchtUserById(long id) {
        Optional<User> userOptinal = this.userRepository.findById(id);
        if (userOptinal.isPresent()) {
            return userOptinal.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec,pageable);

        Meta meta = new Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();

        meta.setPage(pageUser.getNumber()+1);
        meta.setPageSize(pageUser.getSize());

        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());

        result.setMeta(meta);
        result.setResult(pageUser.getContent());
        result.setResult(pageUser);

        return result;
    }

    public User handleUpdateUser(User user) {
        User currentUser = fetchtUserById(user.getId());
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setPassword(user.getPassword());
            currentUser.setEmail(user.getEmail());

        }
        return this.userRepository.save(currentUser);
    }

    public User handlerGetUserbyUserName(String username) {
        return this.userRepository.findByName(username);
    }
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
