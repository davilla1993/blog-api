package com.gbossoufolly.blogapi.services.impl;

import com.gbossoufolly.blogapi.entities.User;
import com.gbossoufolly.blogapi.exceptions.ResourceNotFoundException;
import com.gbossoufolly.blogapi.payloads.UserDTO;
import com.gbossoufolly.blogapi.repositories.UserRepository;
import com.gbossoufolly.blogapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static UserRepository userRepository;
    private static ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = dtoToUser(userDTO);
        User savedUser = userRepository.save(user);

        return userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = userRepository.save(user);
        UserDTO userDTO1 = userToDto(updatedUser);

        return userDTO1;
    }

    @Override
    public UserDTO getUserById(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "Id", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users =  userRepository.findAll();
        List<UserDTO> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

       User user =  userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "Id", userId));

       userRepository.delete(user);

    }


    public static User dtoToUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        /*User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());*/

        return user;
    }

    public static UserDTO userToDto(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        /*UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());*/

        return userDTO;
    }


}
