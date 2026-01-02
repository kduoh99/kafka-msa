package com.study.userservice.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.userservice.api.dto.request.SignUpReqDto;
import com.study.userservice.application.event.UserSignedUpEvent;
import com.study.userservice.domain.User;
import com.study.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	@Transactional
	public void signUp(SignUpReqDto request) {
		User user = userRepository.save(User.builder()
			.email(request.email())
			.name(request.name())
			.password(request.password())
			.build());

		UserSignedUpEvent userSignedUpEvent = new UserSignedUpEvent(
			user.getId(),
			user.getEmail(),
			user.getName()
		);
		this.kafkaTemplate.send("user.signed-up", serialize(userSignedUpEvent));
	}

	private String serialize(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 직렬화 실패");
		}
	}
}
