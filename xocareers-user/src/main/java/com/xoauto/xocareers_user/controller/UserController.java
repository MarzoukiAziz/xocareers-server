package com.xoauto.xocareers_user.controller;

import com.xoauto.xocareers_user.model.JwtResponse;
import com.xoauto.xocareers_user.model.User;
import com.xoauto.xocareers_user.service.UserService;
import com.xoauto.xocareers_user.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder()
                .type("Bearer")
                .username(user.getEmail())
                .token(jwt)
                .build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser != null)
            return ResponseEntity.badRequest().body("User already exists!");

        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Connected");
    }

    @GetMapping("/admin/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.selectAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/admin/id")
    public ResponseEntity<User> getUserById(@RequestParam long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id); // Ensure the ID is set correctly for update
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);

        }
    }

}
