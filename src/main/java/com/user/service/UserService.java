package com.user.service;

import com.user.DTO.UserDTO;
import com.user.model.User;
import com.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

        public User registerUser(User user) {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                throw new RuntimeException("Email is already in use.");
            }
            return userRepository.save(user);
        }

    public Optional<User> loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();

    return users.stream().map(user -> {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }).collect(Collectors.toList());
}

    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        UserDTO dto1 = new UserDTO();
        dto1.setId(user.getId());
        dto1.setFirstName(user.getFirstName());
        dto1.setLastName(user.getLastName());
        dto1.setEmail(user.getEmail());
        return dto1;
    }


public UserDTO editUser(int id, UserDTO userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"+ id));

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser = userRepository.save(existingUser);
        userDto.setId(existingUser.getId());
         return userDto;
    }


    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }



}


