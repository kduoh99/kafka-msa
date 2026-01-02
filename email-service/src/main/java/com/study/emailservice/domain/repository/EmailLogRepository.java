package com.study.emailservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.emailservice.domain.EmailLog;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
}
