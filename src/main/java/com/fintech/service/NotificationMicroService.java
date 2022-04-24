package com.fintech.service;

import com.fintech.entity.AccountDetails;
import com.fintech.entity.TranjactionDetails;
import com.fintech.openapi.model.NotificationDetailsDTO;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface NotificationMicroService {
    NotificationDetailsDTO sendTranjactionNotification(AccountDetails accountDetails, TranjactionDetails tranjactionDetails);
    NotificationDetailsDTO sendNewAccountNotification(AccountDetails accountDetails);
}