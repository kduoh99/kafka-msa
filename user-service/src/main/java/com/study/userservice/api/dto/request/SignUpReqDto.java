package com.study.userservice.api.dto.request;

public record SignUpReqDto(
	String email,
	String name,
	String password
) {
}
