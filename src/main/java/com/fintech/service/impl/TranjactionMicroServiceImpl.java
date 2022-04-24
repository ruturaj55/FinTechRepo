package com.fintech.service.impl;

import com.fintech.entity.AccountDetails;
import com.fintech.entity.TranjactionDetails;
import com.fintech.mapper.TranjactionDetailsMapper;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import com.fintech.repository.AccountDetailsRepository;
import com.fintech.repository.TranjactionDetailsRepository;
import com.fintech.service.NotificationMicroService;
import com.fintech.service.TranjactionMicroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranjactionMicroServiceImpl implements TranjactionMicroService {
    private final TranjactionDetailsMapper tranjactionDetailsMapper;
    private final NotificationMicroService notificationMicroService;
    @Autowired
    private TranjactionDetailsRepository tranjactionDetailsRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Override
    public TranjactionDetailsDTO newTranjaction(TranjactionDetailsDTO tranjactionDetailsDTO) {
        TranjactionDetailsDTO tranjactionDetailsDTOResponse = new TranjactionDetailsDTO();
        log.info("beginning of new tranjaction function");
        TranjactionDetails savednewTranjactionDetails = null;
        try {
            if (tranjactionDetailsDTO.getTranjactionType().equals("withdrawal")) {
                tranjactionDetailsDTOResponse = withdrawal(tranjactionDetailsDTO);
            } else if (tranjactionDetailsDTO.getTranjactionType().equals("deposite")) {
                tranjactionDetailsDTOResponse = deposite(tranjactionDetailsDTO);
            } else {
                log.error("Other Tranjaction Type is Not Allowed");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tranjaction Type is not allowed : " + tranjactionDetailsDTO.getTranjactionType());
            }
            return tranjactionDetailsMapper.toTranjactionDetailsDTO(savednewTranjactionDetails);
        } catch (ResponseStatusException e) {
            log.error("Tranjaction failed , Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened : " + e.getMessage());
        }
    }

    private TranjactionDetailsDTO deposite(TranjactionDetailsDTO tranjactionDetailsDTORequest) {
        TranjactionDetailsDTO tranjactionDetailsDTO1 = new TranjactionDetailsDTO();
        Double withdraAmount = Double.valueOf(tranjactionDetailsDTORequest.getTranjactionAmount());
        AccountDetails accountDetails = accountDetailsRepository.getById(Long.valueOf(tranjactionDetailsDTORequest.getAccountId()));
        double currentBalance = accountDetails.getAccountBalance();
        if (currentBalance >= withdraAmount) {
            currentBalance += withdraAmount;
            accountDetails.setAccountBalance(currentBalance);
            accountDetailsRepository.save(accountDetails);
            TranjactionDetails newTranjactionDetails = tranjactionDetailsMapper.toTranjactionDetailsRequestBody(tranjactionDetailsDTORequest);
            TranjactionDetails savednewTranjactionDetails = tranjactionDetailsRepository.save(newTranjactionDetails);
            tranjactionDetailsDTO1 = tranjactionDetailsMapper.toTranjactionDetailsDTO(savednewTranjactionDetails);
            log.info("Successfully Deposited Amount with new Tranjaction no: " + savednewTranjactionDetails.getTranjactionId());
            notificationMicroService.sendTranjactionNotification(accountDetails, savednewTranjactionDetails);
        }

        return tranjactionDetailsDTO1;
    }

    private TranjactionDetailsDTO withdrawal(TranjactionDetailsDTO tranjactionDetailsDTORequest) {
        TranjactionDetailsDTO tranjactionDetailsDTO1 = new TranjactionDetailsDTO();
        Double withdraAmount = Double.valueOf(tranjactionDetailsDTORequest.getTranjactionAmount());
        AccountDetails accountDetails = accountDetailsRepository.getById(Long.valueOf(tranjactionDetailsDTORequest.getAccountId()));
        double currentBalance = accountDetails.getAccountBalance();
        if (currentBalance >= withdraAmount) {
            currentBalance -= withdraAmount;
            accountDetails.setAccountBalance(currentBalance);
            accountDetailsRepository.save(accountDetails);
            TranjactionDetails newTranjactionDetails = tranjactionDetailsMapper.toTranjactionDetailsRequestBody(tranjactionDetailsDTORequest);
            TranjactionDetails savednewTranjactionDetails = tranjactionDetailsRepository.save(newTranjactionDetails);
            tranjactionDetailsDTO1 = tranjactionDetailsMapper.toTranjactionDetailsDTO(savednewTranjactionDetails);
            log.info("Successfully Withdrawal of amount with Tranjaction no: " + savednewTranjactionDetails.getTranjactionId());
            notificationMicroService.sendTranjactionNotification(accountDetails, savednewTranjactionDetails);
        }

        return tranjactionDetailsDTO1;
    }
}