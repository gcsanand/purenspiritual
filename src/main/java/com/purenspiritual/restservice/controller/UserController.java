package com.purenspiritual.restservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.purenspiritual.restservice.User;
import com.purenspiritual.restservice.UserRepository;
import com.purenspiritual.restservice.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping
    public List < User > getAllUsers() {
        return this.userRepository.findAll();
    }

    // get user by id
    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable(value = "id") long userId) {
        return this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    // create user
    @PostMapping (path = "/user/register")
    public User createUser(@RequestBody User user) {
    	System.out.println("create"+user.getEmail()+user.getFirstName()+user.getLastName()+user.getPassword());
    	
    	ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAny()
    		      .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
    		      .withIgnorePaths("firstName", "lastName", "password");
    	
    	Example<User> example = Example.of(user, caseInsensitiveExampleMatcher);
    	
    	Optional<User> uOptional = userRepository.findOne(example);
    	
    	if (uOptional.isPresent()) {
    		throw new ResourceNotFoundException("User already exists :" + user.getEmail());
    	}
    	return this.userRepository.save(user);
    	
    }
    
    // create user
    @PostMapping (path = "/user/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
    	System.out.println("Login"+user.getEmail()+user.getPassword());
    	
    	ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAny()
    		      .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
    		      .withIgnorePaths("firstName", "lastName", "password");
    	
    	Example<User> example = Example.of(user, caseInsensitiveExampleMatcher);
    	
    	Optional<User> uOptional = userRepository.findOne(example);
    	
    	if (uOptional.isPresent()) {
    		//if (user.getPassword().equals(uOptional)
    		//throw new ResourceNotFoundException("User already exists :" + user.getEmail());
    		if (uOptional.get().getPassword().equals(user.getPassword())) {
    			return new ResponseEntity<>(
    				      "Validation Successful", HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(
  				      "Validation Failed", HttpStatus.NO_CONTENT);
    		}
    		
    	} else {
    		return new ResponseEntity<>(
				      "User does not exist", HttpStatus.CREATED);
    	}
    	    	
    }

    // update user
    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User existingUser = this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
    }

    // delete user by id
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity < User > deleteUser(@PathVariable("id") long userId) {
        User existingUser = this.userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}