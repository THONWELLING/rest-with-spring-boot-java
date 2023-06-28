package com.thonwelling.restwithspringbootjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.HashMap;
//import java.util.Map;

@SpringBootApplication
public class Startup {
	public static void main(String[] args) {

		SpringApplication.run(Startup.class, args);
//		Map<String, PasswordEncoder> encoders = new HashMap<>();
//
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//		encoders.put("bcrypt", bCryptPasswordEncoder);
//		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
//		passwordEncoder.setDefaultPasswordEncoderForMatches(bCryptPasswordEncoder);
//
//		String result1 = passwordEncoder.encode("thondani");
//		String result2 = passwordEncoder.encode("danithon");
//		System.out.println("My hash result1 " + result1);
//		System.out.println("My hash result2 " + result2);
	}
}