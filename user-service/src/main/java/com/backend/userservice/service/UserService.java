package com.backend.userservice.service;

import com.backend.userservice.repository.UserRepository;
import dto.UserDTO;
import exception.RestException;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByIdService(Long userId) throws RestException {

        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isEmpty()) {
            logger.warn("user not found with id : "+ userId + " :: getUserByIdService");
            throw new RestException("user not found", HttpStatus.NOT_FOUND);
        }
        return UserDTO.userToUserDTO(userOp.get());
    }

    public UserDTO saveUserService(UserDTO userDTO) throws RestException {
        var user = UserDTO.userDtoToUser(userDTO);
        try {
            var savedUser = userRepository.save(user);
            return UserDTO.userToUserDTO(savedUser);
        } catch (IllegalArgumentException e) {
            logger.warn("user object cannot be null :: saveUserService");
            throw new RestException("user object cannot be null", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("internal server error :: saveUserService :: "+ e.getMessage());
            throw new RestException("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public UserDTO updateUserByIdService(Long userId, UserDTO userDTO) throws RestException {

        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isEmpty()) {
            logger.warn("user not found :: updateUserByIdService");
            throw new RestException("user not found", HttpStatus.NOT_FOUND);
        }

        var userToUpdate = userOp.get();
        userToUpdate.setUserName(userDTO.getUserName() == null ? userToUpdate.getUserName() : userDTO.getUserName());
        userToUpdate
                .setUserEmail(userDTO.getUserEmail() == null ? userToUpdate.getUserEmail() : userDTO.getUserEmail());
        var updatedUser = userRepository.save(userToUpdate);
        return UserDTO.userToUserDTO(updatedUser);
    }

    public void deleteUserService(Long userId) throws RestException {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("user not found :: updateUserByIdService");
            throw new RestException("user not found", HttpStatus.NOT_FOUND);
        }
    }
}
