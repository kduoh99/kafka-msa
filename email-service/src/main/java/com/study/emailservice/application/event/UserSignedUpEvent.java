package com.study.emailservice.application.event;

public record UserSignedUpEvent(
	Long userId,
	String email,
	String name
) {
}
