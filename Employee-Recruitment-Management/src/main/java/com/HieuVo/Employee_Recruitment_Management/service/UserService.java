package com.HieuVo.Employee_Recruitment_Management.service;

import com.HieuVo.Employee_Recruitment_Management.Model.User;
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

    public List<User> fetchAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        User currentUser = fetchtUserById(user.getId());
        if (currentUser != null) {
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());
            currentUser.setEmail(user.getEmail());

        }
        return this.userRepository.save(currentUser);
    }

    public User handlerGetUserbyUserName(String username) {
        return this.userRepository.findByUsername(username);
    }
}
