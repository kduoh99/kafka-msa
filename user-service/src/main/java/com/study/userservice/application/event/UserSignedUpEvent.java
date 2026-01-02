package com.study.userservice.application.event;

public record UserSignedUpEvent(
	Long userId,
	String email,
	String name
) {
}
