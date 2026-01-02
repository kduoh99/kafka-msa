package com.study.emailservice.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.emailservice.application.event.UserSignedUpEvent;
import com.study.emailservice.domain.EmailLog;
import com.study.emailservice.domain.repository.EmailLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSignedUpConsumer {
	private final EmailLogRepository emailLogRepository;
	private final ObjectMapper objectMapper;

	@KafkaListener(
		topics = "user.signed-up",
		groupId = "email-service",
		concurrency = "3"
	)
	@RetryableTopic(
		attempts = "5",
		backoff = @Backoff(delay = 1000, multiplier = 2),
		dltTopicSuffix = ".dlt"
	)
	public void consume(String message) throws InterruptedException {
		UserSignedUpEvent userSignedUpEvent = deserialize(message);

		String receiverEmail = userSignedUpEvent.email();
		String subject = userSignedUpEvent.name() + "님, 회원 가입을 축하드립니다!";
		Thread.sleep(3000);
		System.out.println("이메일 발송 완료");

		emailLogRepository.save(EmailLog.builder()
			.receiverUserId(userSignedUpEvent.userId())
			.receiverEmail(receiverEmail)
			.subject(subject)
			.build());
	}

	private UserSignedUpEvent deserialize(String json) {
		try {
			return objectMapper.readValue(json, UserSignedUpEvent.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 역직렬화 실패");
		}
	}
}
