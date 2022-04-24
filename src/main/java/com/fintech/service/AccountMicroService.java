package com.fintech.service;

import com.fintech.openapi.model.AccountDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface AccountMicroService {

    AccountDetailsDTO createNewAccount(AccountDetailsDTO accountDetailsDTO);
    AccountDetailsDTO getAccountDetails(String accountId);
    AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO);

}