package com.ssmedia.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssmedia.model.Post;
import com.ssmedia.model.User;
import com.ssmedia.repo.PostRepo;
import com.ssmedia.repo.UserRepo;
import com.ssmedia.rest.Response;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@RestController
public class SSMediaController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	Response response;

	@PostConstruct
	public void loadData() {
		User user1 = new User("john_doe", "test1@gmail.com", "password123", "1990-01-01");
		User user2 = new User("jane_smith", "test2@gmail.com", "securepass", "1992-02-02");
		User user3 = new User("alice_wonder", "test3@gmail.com", "alicepwd", "1995-03-03");

		List<User> users = Arrays.asList(user1, user2, user3);
		int i = 1;
		for (User user : users) {
			userRepo.save(user);
			Post post1 = new Post("First Post" + i, "This is the content of the first post." + i, "john_doe" + i,
					"2024-01-01", user.getId());
			postRepo.save(post1);
			i = i + 1;
		}

		System.out.println("Sample users loaded into the database.");
		System.out.println("Sample posts loaded into the database.");

	}

	@GetMapping("/hello")
	public Response hello() {
		
		User u1 = new User();
		u1.setUsername("test1");
		
		User u2 = new User();
		u2.setUsername("test1");
		
	    Response response = new Response("Hello World", 200, "success");
		return response;
	}
	
	@GetMapping("/totalCount")
	public Response userCount() {
		return new Response(userRepo.count(), 200, "success");
	}
	
	@GetMapping("/uniqueUsernames")
	public Response uniqueUsernames() {
		return new Response<>(userRepo.findAllUsernames(), 200, "success");
	}

	@PostMapping("/signUp")
	public Response signUp(@RequestBody User user) {		
		if(!user.getEmail().contains("@")) {
			response.setResponse(null);
			response.setCode(500);
			response.setMessage("invalidEmail");
			return response;
		}
		try {
			User dbResponse = userRepo.save(user);
			response.setResponse(dbResponse);
			response.setCode(200);
			response.setMessage("Success");
		} catch (Exception e) {
			response.setResponse(null);
			response.setCode(500);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@GetMapping("/getAllUsers")
	public Response getAllUsers() {
		List<User> users = userRepo.findAll(); //1sec
		for (User user : users) {
			System.out.println("User: " + user.getUsername() + ", Email: " + user.getEmail());
			List<Post> posts = postRepo.findByUserId(user.getId()); // 1s sec
			user.setPosts(posts);
			posts.stream().sorted((a,b) -> a.getUserId() - b.getUserId());
		}
		return new Response(users, 200, "success");
	}

	@GetMapping("/getUserById/{id}")
	public Response getUserById(@PathVariable Long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			List<Post> posts = postRepo.findByUserId(user.get().getId());
			user.get().setPosts(posts);
		}
		return new Response(user,200,"success");
	}

	@GetMapping("/deleteUserById/{id}")
	public String deleteUserById(@PathVariable Long id) {
		String response = null;
		try {
			Optional<User> user = userRepo.findById(id);
			userRepo.delete(user.get());
			response = "User with ID " + id + " deleted successfully.";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = "Error occured while deleting user with ID " + id + ": " + e.getMessage();
		}
		return response;
	}

	@PostMapping("/updateUser")
	public String updateUser(@RequestBody User user) {
		String response = null;
		try {
			userRepo.save(user);
			//postRepo.save(user.getPosts().get(0));
			response = "User " + user.getUsername() + " updated successfully.";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = "Error occured while updating user " + user.getUsername() + ": " + e.getMessage();
		}
		return response;
	}

	@PostMapping("/login")
	public User login(@RequestBody User request) {
		String username = request.getUsername();
		String password = request.getPassword();
		List<User> res = userRepo.findByUsernameAndPassword(username, password);
		if(res.isEmpty()) {
			return null;
		}
		User response = res.get(0);
		if (response != null) {
			List<Post> posts = postRepo.findByUserId(response.getId());
			response.setPosts(posts);
			return response;
		} else {
			return null;
		}
	}

}
