package com.tw.apistackbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@SpringBootApplication
public class ApiStackBaseApplication {

	@GetMapping(path = "/companies")
	public HashMap<String,String> getCompanies(){
		HashMap<String,String> map = new HashMap<>();
		map.put("user","name");
		return  map;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiStackBaseApplication.class, args);
	}
}
