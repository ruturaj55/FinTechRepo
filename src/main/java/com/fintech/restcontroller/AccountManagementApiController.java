package com.fintech.restcontroller;

import com.fintech.openapi.api.AccountManagementApi;
import com.fintech.openapi.model.AccountDetailsDTO;
import com.fintech.service.AccountMicroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountManagementApiController implements AccountManagementApi {
    private final AccountMicroService accountMicroService;

    @Override
    public ResponseEntity<AccountDetailsDTO> createNewAccount(AccountDetailsDTO accountDetailsDTO) {
        return ResponseEntity.ok(accountMicroService.createNewAccount(accountDetailsDTO));
    }

    @Override
    public ResponseEntity<AccountDetailsDTO> getAccountHolder(String accNumber) {
        return ResponseEntity.ok(accountMicroService.getAccountDetails(accNumber));
    }

    @Override
    public ResponseEntity<AccountDetailsDTO> updateAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        return ResponseEntity.ok(accountMicroService.updateAccountDetails(accountDetailsDTO));
    }
}