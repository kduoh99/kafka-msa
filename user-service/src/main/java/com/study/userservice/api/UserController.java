package com.study.userservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.userservice.api.dto.request.SignUpReqDto;
import com.study.userservice.application.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	public ResponseEntity<String> signUp(@RequestBody SignUpReqDto request) {
		userService.signUp(request);
		return ResponseEntity.ok("회원가입 성공");
	}
}
