package com.backend.userservice.controller;

import com.backend.userservice.service.UserService;
import dto.UserDTO;
import exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) throws RestException {
        var user = userService.getUserByIdService(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user) throws RestException {
        var savedUserDTO = userService.saveUserService(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Long userId, @RequestBody UserDTO user) throws RestException {
        var updateUserDTO = userService.updateUserByIdService(userId, user);
        return ResponseEntity.status(HttpStatus.OK).body(updateUserDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) throws RestException {
        userService.deleteUserService(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
