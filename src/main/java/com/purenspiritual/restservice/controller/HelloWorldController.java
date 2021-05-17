package com.purenspiritual.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.purenspiritual.restservice.User;
import com.purenspiritual.restservice.UserRepository;
import com.purenspiritual.restservice.beans.HelloWorldBean;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HelloWorldController {


    @Autowired
    private UserRepository userRepository;
    
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World !!";
	}

	@PostMapping(path = "/posthello-world")
	public String postHelloWorld(@RequestBody User userDetails) {
		System.out.println("Post Hello World !!"+userDetails.getEmail()+userDetails.getPassword());
		
		return "Post Hello World !!";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean!");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		System.out.println("reached server method");
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

}