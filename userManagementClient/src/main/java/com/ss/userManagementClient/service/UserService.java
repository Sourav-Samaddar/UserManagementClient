package com.ss.userManagementClient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ss.userManagementClient.dto.User;
import java.util.List;

@FeignClient(name = "${service.name}", url = "${service.base.url}")
public interface UserService {

    @GetMapping("/user/{id}")
    User getUser(@PathVariable("id") String id);

    @GetMapping(value = "/users")
    List<User> getUserList();


    @PostMapping("/user")
    String addUser(User user);

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable("id") String id);

    @PutMapping("/user/{id}")
    String updateAddress(@PathVariable("id") String id, @RequestParam("address") String newAddress);

 }
