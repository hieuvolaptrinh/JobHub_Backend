package com.HieuVo.JobHub_BE.Service;

import com.HieuVo.JobHub_BE.DTO.Meta;
import com.HieuVo.JobHub_BE.DTO.Response.ResCreateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResUpdateUserDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResUserDTO;
import com.HieuVo.JobHub_BE.DTO.ResultPaginationDTO;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    begin convert to DTO
    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        ResUserDTO.CompanyUser companyUser = new ResUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAge(user.getAge());
        res.setAddress(user.getAddress());
        res.setName(user.getName());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());

//        if (UserLogin.getCompany() != null) {
//            companyUser.setId(UserLogin.getCompany().getId());
//            companyUser.setName(UserLogin.getCompany().getName());
//
//            res.setCompany(companyUser);
//        }

        return res;
    }
    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
//        ResUpdateUserDTO.CompanyUser companyUser = new ResUpdateUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
//
//        if (UserLogin.getCompany() != null) {
//            companyUser.setId(UserLogin.getCompany().getId());
//            companyUser.setName(UserLogin.getCompany().getName());
//
//            res.setCompany(companyUser);
//        }
        return res;
    }
    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        ResCreateUserDTO.CompanyUser companyUser = new ResCreateUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());

//        if (UserLogin.getCompany() != null) {
//            companyUser.setId(UserLogin.getCompany().getId());
//
//            res.setCompany(companyUser);
//        }

        return res;
    }

//    end convert to DTO
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User handleCreateUser(User user) throws Exception {
        if (this.userRepository.existsByEmail(user.getEmail()) == true) {
            throw new Exception("User exists with this email");
        }
        return this.userRepository.save(user);

    }

    public Void handleDeleteUser(long id) throws Exception {
        if (!this.userRepository.existsById(id)) {
            throw new Exception("User not found") ;
        }
        this.userRepository.deleteById(id);
        return null;
    }

    public User handleFecthUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public ResultPaginationDTO handleFetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        Meta meta = new Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());

        result.setMeta(meta);

        List<ResUserDTO> listResUserDTO = pageUser.getContent().stream()
                .map(this::convertToResUserDTO)
                .collect(Collectors.toList());
// or
//        List<ResUserDTO> listResUserDto= pageUser.getContent()
//                        .stream().map(item-> new ResUserDTO(
//                        item.getId(),
//                        item.getEmail(),
//                        item.getName(),
//                        item.getGender(),
//
//                        item.getAddress(),
//                        item.getAge(),
//                        item.getUpdatedAt(),
//                        item.getCreatedAt()

//                )).collect(Collectors.toList());

        result.setResult(pageUser.getContent());
        result.setResult(pageUser);

        return result;
    }

    public User handleUpdateUser(User user) {
        User newUser= null;
        User currentUser = this.handleFecthUserById(user.getId());
        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setGender(user.getGender());
            currentUser.setAge(user.getAge());
            currentUser.setName(user.getName());
//            if (currentUser.getCompany() != null) {
//                Optional<Company> companyOptional = this.companyService.findById(currentUser.getCompany().getId());
//                currentUser.setCompany(companyOptional.orElse(null));
//            }
             newUser = this.userRepository.save(currentUser);
        }
        return newUser;
    }

    public User handlerGetUserbyUserName(String username) {
        return this.userRepository.findByName(username);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
//    update user token
    public void updateUserToken(String token, String email){
        User currenUser= this.userRepository.findByEmail(email);
        if(currenUser != null){
            currenUser.setRefreshToken(token);
            this.userRepository.save(currenUser);

        }
    }
}
