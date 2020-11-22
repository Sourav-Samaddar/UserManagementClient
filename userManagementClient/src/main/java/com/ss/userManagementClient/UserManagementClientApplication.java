package com.ss.userManagementClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserManagementClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementClientApplication.class, args);
	}

}
