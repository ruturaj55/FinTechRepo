package com.fintech.service.impl;

import com.fintech.entity.AccountDetails;
import com.fintech.mapper.AccountDetailsMapper;
import com.fintech.openapi.model.AccountDetailsDTO;
import com.fintech.repository.AccountDetailsRepository;
import com.fintech.service.AccountMicroService;
import com.fintech.service.NotificationMicroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountMicroServiceImpl implements AccountMicroService {
    private final AccountDetailsMapper accountDetailsMapper;
    private final NotificationMicroService notificationMicroService;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Override
    public AccountDetailsDTO createNewAccount(AccountDetailsDTO accountDetailsDTO) {
        log.info("beginning of Create new Account function");
        try {
            AccountDetails accountDetails = accountDetailsMapper.toAccountDetailsRequestBody(accountDetailsDTO);
            AccountDetails savedAccountDetails = accountDetailsRepository.save(accountDetails);
            log.info("Successfully Created Account no: " + savedAccountDetails.getAccountId());
           // notificationMicroService.sendNewAccountNotification(accountDetails);
            return accountDetailsMapper.toAccountDetailsDTORequestBody(savedAccountDetails);
        } catch (ResponseStatusException e) {
            log.error("New Account creation failed , Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened : " + e.getMessage());
        }
    }

    @Override
    public AccountDetailsDTO getAccountDetails(String accountId) {
        log.info("beginning of Create new Account function");
        try {
            AccountDetails savedAccountDetails = accountDetailsRepository.getById(Long.valueOf(accountId));
            return accountDetailsMapper.toAccountDetailsDTORequestBody(savedAccountDetails);
        } catch (ResponseStatusException e) {
            log.error("Account Could not find with Acc Id : " + accountId);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened : " + e.getMessage());
        }
    }

    @Override
    public AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        log.info("beginning of Update existing Account function");
        try {
            AccountDetails currentAccountDetails = accountDetailsRepository.getById(Long.valueOf(accountDetailsDTO.getAccountId()));
            AccountDetails accountDetails = accountDetailsMapper.toAccountDetailsRequestBody(accountDetailsDTO);
            AccountDetails savedAccountDetails = accountDetailsRepository.save(accountDetails);
            log.info("Successfully Created Account no: " + savedAccountDetails.getAccountId());
            return accountDetailsMapper.toAccountDetailsDTORequestBody(savedAccountDetails);
        } catch (ResponseStatusException e) {
            log.error("Account Updation failed , Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened : " + e.getMessage());
        }
    }
}