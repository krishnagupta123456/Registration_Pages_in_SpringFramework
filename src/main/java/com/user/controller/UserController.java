package com.user.controller;

import com.user.DTO.UserDTO;
import com.user.model.User;
import com.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<User> loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        return loggedInUser.isPresent() ? ResponseEntity.ok("Login successful.") :
                ResponseEntity.status(401).body("Invalid email or password.");
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getName(){
//        return ResponseEntity.ok(userService.getUser());
//    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public UserDTO getStateById(@PathVariable int id) {
        return userService.getUserById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable int id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.editUser(id, user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
