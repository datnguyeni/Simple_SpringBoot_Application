package com.example.revise.controller;

import com.example.revise.dto.ApiResponse;
import com.example.revise.dto.RequestDTO;
import com.example.revise.dto.ResponseDTO;
import com.example.revise.entity.User;
import com.example.revise.mapper.UserMapper;
import com.example.revise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final  UserService userService;
    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ApiResponse<ResponseDTO> addUser(@RequestBody RequestDTO requestDTO){
        User user = userMapper.toUser(requestDTO);
        User saved = userService.createUser(user);

        ResponseDTO data = userMapper.toResponseDTO(user);

        return new ApiResponse<>(200, "Them thanh cong", data);
    }

//    @GetMapping("/get-all")
//    public List<ResponseDTO> getAllUser() {
//        List<User> users = userService.getAllUsers();
//        return userMapper.toResponseList(users);
//    }

    @GetMapping("/get-all")
    public ApiResponse<List<ResponseDTO>> getAllUser() {
        List<User> users = userService.getAllUsers();
        List<ResponseDTO> data = userMapper.toResponseList(users);

        return new ApiResponse<>(200, "Lấy danh sách thành công", data);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody RequestDTO requestDTO) {

        User updated = userService.updateUser(id, requestDTO);

        return ResponseEntity.ok(
                userMapper.toResponseDTO(updated)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete user successfully");
    }




}
