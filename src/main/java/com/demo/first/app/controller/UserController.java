package com.demo.first.app.controller;

import com.demo.first.app.service.UserService;
import com.demo.first.app.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserService();


    @PostMapping
    public ResponseEntity<User> cretaeUser(@RequestBody User user) {

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updated = userService.updateUser(user);
        if(updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);

        if(!isDeleted)
                return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(value = "userId", required = false) int id) {
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User> getUserOrders(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ) {
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
            @RequestParam(required = false, defaultValue = "alice") String name,
            @RequestParam(required = false, defaultValue = "email.com") String email) {

        return ResponseEntity.ok(userService.searchUsers(name, email));
    }

    @GetMapping("/info/{id}")
    public String getUserInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent) {
        return "User Agent: " + userAgent
                + " : " + id
                + " : " + name;
    }
}
