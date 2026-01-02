package com.study.emailservice.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserSignedUpDLTConsumer {

	@KafkaListener(
		topics = "user.signed-up.dlt",
		groupId = "email-service"
	)
	public void consume(String message) {
		System.out.println("DLT 수신 메시지: " + message);

		// 로그, 알림 처리
		System.out.println("로그 시스템 전송: " + message);
		System.out.println("Slack 알림 발송");
	}
}
