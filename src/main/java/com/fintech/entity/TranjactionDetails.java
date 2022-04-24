package com.fintech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tranjactions")
public class TranjactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tranjactionId;
    private String tranjactionType;
    private String tranjactionAmount;
    private String availableBalance;
    private LocalDateTime tranjactionDateTime;
    private String tranjactionRemark;
    private Long accountId;
}