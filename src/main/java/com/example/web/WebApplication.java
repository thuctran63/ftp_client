package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.server.FTPServer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.server","com.example.web"})
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
		FTPServer.getInstance().startServer();
	}
}
