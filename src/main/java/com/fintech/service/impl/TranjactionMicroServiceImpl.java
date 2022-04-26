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
        try {
            if (tranjactionDetailsDTO.getTranjactionType().equals("withdrawal")) {
                tranjactionDetailsDTOResponse = withdrawal(tranjactionDetailsDTO);
            } else if (tranjactionDetailsDTO.getTranjactionType().equals("deposite")) {
                tranjactionDetailsDTOResponse = deposite(tranjactionDetailsDTO);
            } else {
                log.error("Other Tranjaction Type is Not Allowed");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tranjaction Type is not allowed : " + tranjactionDetailsDTO.getTranjactionType());
            }
            return tranjactionDetailsDTOResponse;
        } catch (ResponseStatusException e) {
            log.error("Tranjaction failed , Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception happened : " + e.getMessage());
        }
    }

    /**
     * Function to perform Deposite type of tranjaction
     *
     * @param tranjactionDetailsDTORequest
     * @return
     */
    private TranjactionDetailsDTO deposite(TranjactionDetailsDTO tranjactionDetailsDTORequest) {
        TranjactionDetailsDTO tranjactionDetailsDTO = new TranjactionDetailsDTO();
        Double withdraAmount = Double.valueOf(tranjactionDetailsDTORequest.getTranjactionAmount());
        AccountDetails accountDetails = accountDetailsRepository.getById(Long.valueOf(tranjactionDetailsDTORequest.getAccountId()));
        double currentBalance = accountDetails.getAccountBalance();
        if (tranjactionDetailsDTORequest.getTranjactionAmount() != 0) {
            currentBalance += withdraAmount;
            accountDetails.setAccountBalance(currentBalance);
            accountDetailsRepository.save(accountDetails);
            TranjactionDetails newTranjactionDetails = tranjactionDetailsMapper.toTranjactionDetailsRequestBody(tranjactionDetailsDTORequest);
            newTranjactionDetails.setAvailableBalance(currentBalance);
            TranjactionDetails savednewTranjactionDetails = tranjactionDetailsRepository.save(newTranjactionDetails);
            tranjactionDetailsDTO = tranjactionDetailsMapper.toTranjactionDetailsDTO(savednewTranjactionDetails);
            log.info("Successfully Deposited Amount with new Tranjaction no: " + savednewTranjactionDetails.getTranjactionId());
            // notificationMicroService.sendTranjactionNotification(accountDetails, savednewTranjactionDetails);
        }

        return tranjactionDetailsDTO;
    }

    /**
     * Function to perform withdrawal type of tranjaction
     *
     * @param tranjactionDetailsDTORequest
     * @return
     */
    private TranjactionDetailsDTO withdrawal(TranjactionDetailsDTO tranjactionDetailsDTORequest) {
        TranjactionDetailsDTO tranjactionDetailsDTO = new TranjactionDetailsDTO();
        Double withdraAmount = Double.valueOf(tranjactionDetailsDTORequest.getTranjactionAmount());
        AccountDetails accountDetails = accountDetailsRepository.getById(tranjactionDetailsDTORequest.getAccountId());
        double currentBalance = accountDetails.getAccountBalance();
        if (currentBalance >= withdraAmount) {
            currentBalance -= withdraAmount;
            accountDetails.setAccountBalance(currentBalance);
            accountDetailsRepository.save(accountDetails);
            TranjactionDetails newTranjactionDetails = tranjactionDetailsMapper.toTranjactionDetailsRequestBody(tranjactionDetailsDTORequest);
            newTranjactionDetails.setAvailableBalance(currentBalance);
            TranjactionDetails savednewTranjactionDetails = tranjactionDetailsRepository.save(newTranjactionDetails);
            tranjactionDetailsDTO = tranjactionDetailsMapper.toTranjactionDetailsDTO(savednewTranjactionDetails);
            log.info("Successfully Withdrawal of amount with Tranjaction no: " + savednewTranjactionDetails.getTranjactionId());
            // notificationMicroService.sendTranjactionNotification(accountDetails, savednewTranjactionDetails);
        }

        return tranjactionDetailsDTO;
    }
}