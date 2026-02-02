package com.example.revise.service;

import com.example.revise.dto.RequestDTO;
import com.example.revise.dto.ResponseDTO;
import com.example.revise.entity.User;
import com.example.revise.exception.AppException;
import com.example.revise.exception.ErrorCode;
import com.example.revise.mapper.UserMapper;
import com.example.revise.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public  UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // hash password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);
        return user;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //required transactional
    @Transactional
    public User updateUser(Long id, RequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntity(dto, user);

        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getMyInfo() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current Auth: " + auth.getName());
        // 1. Lấy thông tin từ SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // 2. Tìm kiếm trong Database dựa trên name đã lấy
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
