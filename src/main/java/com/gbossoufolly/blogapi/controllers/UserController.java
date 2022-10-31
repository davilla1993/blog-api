package com.gbossoufolly.blogapi.controllers;

import com.gbossoufolly.blogapi.payloads.ApiResponse;
import com.gbossoufolly.blogapi.payloads.UserDTO;
import com.gbossoufolly.blogapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = userService.createUser(userDTO);

        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
                                              @PathVariable("userId") Integer userId) {

       UserDTO updatedUser =  userService.updateUser(userDTO, userId);

       return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(
                            "User deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userId) {

        UserDTO user = userService.getUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
