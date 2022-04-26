package com.fintech.service.impl;

import com.fintech.entity.AccountDetails;
import com.fintech.repository.AccountDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Value("security.default.username")
    private String defaultAdminUserName;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(getAccountDetails(username).getEmailId()) || username.equals(defaultAdminUserName)) {
            return new User(getAccountDetails(username).getEmailId(), getAccountDetails(username).getPassword(), new ArrayList<>());
        } else {
            log.error("Account user not found , Please check error logs...");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account Not available");
        }
    }

    private AccountDetails getAccountDetails(String emailId) {
        AccountDetails accountDetailsBymailId = null;
        try {
            accountDetailsBymailId = accountDetailsRepository.findByEmailId(emailId);
        } catch (Exception e) {
            log.error("Account not found , Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account Not found : " + e.getMessage());
        }
        return accountDetailsBymailId;
    }
}