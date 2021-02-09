package com.example.demo.swaggerdemo.controller;


import com.example.demo.swaggerdemo.exception.ResourceNotFoundException;
import com.example.demo.swaggerdemo.model.User;
import com.example.demo.swaggerdemo.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;


    @PostMapping("/users")
    public String createUser(@Valid @RequestBody User user) {
        String message = "User data inserted into DB";
        logger.info("Adding User to the database : ");
        userRepository.save(user);
        logger.info(message);
        return message;
    }

    @GetMapping("/users")
    public List<User> getUser() {
        logger.info("Getting all user from database : ");

        List<User> data = new LinkedList<>();
        data = userRepository.findAll();
        logger.debug("data : ", data);

        System.out.println("data :  " + data);
        return data;


    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        // Optional<User> user;

        try{
            logger.info("Getting all user from database : ");
             return userRepository.findById(userId).get();
        }
        catch (Exception e){
            // user = Optional.empty();
            logger.error(e);
            throw new ResourceNotFoundException( "Id not present", "-> ", userId);
        }




            //    .orElseThrow(() -> new ResourceNotFoundException("User", "Id not found", userId));

    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId not found", " - ", userId));

        user.setFname(userDetails.getFname());
        user.setLname(userDetails.getLname());
        user.setAddress(userDetails.getAddress());
        user.setCity(userDetails.getCity());
        user.setCountry(userDetails.getCountry());

        User updatedUser = userRepository.save(user);


        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found" ," - ", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }


}
