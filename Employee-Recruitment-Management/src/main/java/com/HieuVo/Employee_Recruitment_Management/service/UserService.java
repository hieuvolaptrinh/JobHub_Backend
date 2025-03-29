package com.HieuVo.Employee_Recruitment_Management.service;

import com.HieuVo.Employee_Recruitment_Management.domain.User;
import com.HieuVo.Employee_Recruitment_Management.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
       return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }
}
