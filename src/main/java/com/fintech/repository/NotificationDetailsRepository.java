package com.fintech.repository;

import com.fintech.entity.NotificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDetailsRepository extends JpaRepository<NotificationDetails, Long> {
}