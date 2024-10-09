package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.User;

import com.example.demo.Service.UserServices;

@RestController
public class UserController {

    @Autowired
    private UserServices userService;

    @PutMapping("api/shared/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestParam String email, @RequestParam String newPassword) {
        String message = userService.updatePassword(email, newPassword);
        return ResponseEntity.ok(message);
    }

    @GetMapping("api/admin/userDetailsById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserId(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("api/admin/allUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list = userService.getAllUser();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("api/shared/deleteAccount/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String message = userService.deleteUser(userId);
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("api/admin/allUsersWithRoleUser")
    public ResponseEntity<List<User>> getAllUsersWithRoleUser() {
        List<User> users = userService.getUsersByRole("USER");
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("api/admin/allUsersWithRoleHotelOwner")
    public ResponseEntity<List<User>> getAllUsersWithRoleHotelOwner() {
        List<User> users = userService.getUsersByRole("HOTEL_OWNER");
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}