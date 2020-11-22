package com.ss.userManagementClient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ss.userManagementClient.dto.User;
import com.ss.userManagementClient.service.UserService;

@RestController
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate1; //This is configured using Config.java
	
	static RestTemplate restTemplate = new RestTemplate();
	static String baseURL = "http://localhost:8083/springDataDemo";
	
	@GetMapping("/test")
	public String testApi() {
		return "I am ninja";
	}
	
	@GetMapping("/userAsString/{id}")
	public String getUserByIdByAsString(@PathVariable String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate1.exchange("/user/"+id,
				HttpMethod.GET,requestEntity,String.class);
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		String user = responseEntity.getBody();
		
		return user + statusCode;
	}
	
	@GetMapping("/userAsObject/{id}")
	public User getUserByIdByAsObject(@PathVariable String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<User> responseEntity = restTemplate1.exchange("/user/"+id,
				org.springframework.http.HttpMethod.GET,requestEntity,User.class);
		
		User user = responseEntity.getBody();
		
		return user;
	}
	
	@GetMapping("/allUserAsString")
	public String getAllUserAsString() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate1.exchange("/users",
				org.springframework.http.HttpMethod.GET,requestEntity,String.class);
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		String user = responseEntity.getBody();
		
		return user + statusCode;
	}
	
	@GetMapping("/allUserAsObject")
	public List<User> getAllUserAsObject() {
		System.out.println("***********All User***************");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<List<User>> responseEntity = restTemplate1.exchange(
					"/users",
					HttpMethod.GET,
					requestEntity,
					new ParameterizedTypeReference<List<User>>(){});
		
		List<User> users = responseEntity.getBody();
		
		return users;
	}
	
	@PostMapping("/addUserDetails")
	public String addUserDetails(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(user,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL + "/user",
				HttpMethod.POST,requestEntity,String.class);
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		return statusCode+"";
	}
	
	@PutMapping("/updateUserAddressDetails/{id}")
	public String updateUserAddressDetails(@PathVariable Long id, @RequestParam String address) {
		System.out.println("**********Inside :"+address);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		String url = baseURL + "/user/"+id;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam("address", address);
		System.out.println("Url String :"+builder.toUriString());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(),
				org.springframework.http.HttpMethod.PUT,requestEntity,String.class);
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		return statusCode+"";
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable String id) {
		System.out.println("**********Inside :"+id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
				
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL + "/user/"+id,
				org.springframework.http.HttpMethod.DELETE,requestEntity,String.class);
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		return statusCode+"";
	}
	
	@GetMapping("feignclient/user/{id}")
    public User user(@PathVariable("id") String id){
        logger.info("calling userList() to get list of users");
        return userService.getUser(id);
    }

    @GetMapping("feignclient/userList")
    public List<User> userList(){
        logger.info("calling userList() to get list of users");
        return userService.getUserList();
    }
    
    @PostMapping("feignclient/user")
    public String addUser(@RequestBody User user){
        logger.info("adding user");
        return userService.addUser(user);
    }
    @DeleteMapping("feignclient/user/{id}")
    String deleteUserFieign(@PathVariable String id){
        logger.info("deleting user - {}", id);
        return userService.deleteUser(id);
    }

    @PutMapping("feignclient/updateAddress/{id}")
    String updateAddress(@PathVariable String id, @RequestParam String address){
        logger.info("updating user - {}", id);
        return userService.updateAddress(id, address);
    }
	
}
