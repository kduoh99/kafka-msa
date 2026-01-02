package com.study.emailservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "email_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailLog {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long receiverUserId;

	private String receiverEmail;

	private String subject;

	@Builder
	private EmailLog(Long receiverUserId, String receiverEmail, String subject) {
		this.receiverUserId = receiverUserId;
		this.receiverEmail = receiverEmail;
		this.subject = subject;
	}
}
