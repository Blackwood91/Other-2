package com.example.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/me")
	public Authentication getMe(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("/provaAccessoPagina")
	public ResponseEntity<Map<String, Object>> prova() {
        HashMap<String, Object> response = new HashMap<>();
		response.put("message", "ritoeno back");
		return ResponseEntity.ok(response);		
	}


	
}
