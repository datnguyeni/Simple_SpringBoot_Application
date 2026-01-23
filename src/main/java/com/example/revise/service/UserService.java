package com.example.revise.service;

import com.example.revise.dto.RequestDTO;
import com.example.revise.dto.ResponseDTO;
import com.example.revise.entity.User;
import com.example.revise.mapper.UserMapper;
import com.example.revise.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> getAllUsers() {
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


}
