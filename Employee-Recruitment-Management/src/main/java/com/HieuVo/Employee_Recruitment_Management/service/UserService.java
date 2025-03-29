package com.HieuVo.Employee_Recruitment_Management.service;

import com.HieuVo.Employee_Recruitment_Management.domain.User;
import com.HieuVo.Employee_Recruitment_Management.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchtUserById(long id) {
        Optional<User> userOptinal = this.userRepository.findById(id);
        if (userOptinal.isPresent()) {
            return userOptinal.get();
        }
        return null;
    }

    public List<User> fetchAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        User currentUser = fetchtUserById(user.getId());
        if (currentUser != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            currentUser.setName(user.getName());

        }
        return this.userRepository.save(currentUser);
    }
}
