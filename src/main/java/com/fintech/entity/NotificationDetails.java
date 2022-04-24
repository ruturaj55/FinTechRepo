package com.fintech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificaionId;
    private String tranjactionId;
    private String accountId;
    private String notificaionMessage;
    private String emailId;
}