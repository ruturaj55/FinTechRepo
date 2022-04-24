package com.fintech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountType;
    private String firstName;
    private String lastName;
    private double accountBalance;
    private String emailId;
    private String password;
    private String remark;
    private Date dateOfBirth;


}