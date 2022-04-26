package com.fintech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private double tranjactionAmount;
    private double availableBalance;
    private Date tranjactionDate;
    private String tranjactionRemark;
    private Long accountId;
}