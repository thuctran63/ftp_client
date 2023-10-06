package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.web.server.FTPServer;

@SpringBootApplication

public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
		new Thread(){
			public void run(){
				FTPServer.getInstance().startServer();
			}
		}.start();
	
	}
}
