package com.fintech.service.impl;

import com.fintech.entity.AccountDetails;
import com.fintech.entity.TranjactionDetails;
import com.fintech.openapi.model.NotificationDetailsDTO;
import com.fintech.service.NotificationMicroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationMicroServiceImpl implements NotificationMicroService {
    @Autowired
    private JavaMailSender emailSender;
    @Value("email.frommailid")
    private String fromMailId;
    @Value("${email.account.created.subject}")
    private String accCreatedSubject;
    @Value("${email.account.tranjaction.subject}")
    private String tranjactionSubject;

    @Override
    public NotificationDetailsDTO sendTranjactionNotification(AccountDetails accountDetails, TranjactionDetails tranjactionDetails) {
        try {
            SimpleMailMessage simpleMailMessage = prepareTranjactionMessage(accountDetails, tranjactionDetails);
            emailSender.send(simpleMailMessage);
            return new NotificationDetailsDTO().firstName(accountDetails.getFirstName()).notificationMessage(simpleMailMessage.getText()).availableBalance(String.valueOf(accountDetails.getAccountBalance()));
        } catch (Exception e) {
            log.error("Notification cant send, Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Notification Service Failed due to: " + e.getMessage());
        }

    }

    @Override
    public NotificationDetailsDTO sendNewAccountNotification(AccountDetails accountDetails) {
        try {
            SimpleMailMessage simpleMailMessage = prepareAccCreatedMessage(accountDetails);
            emailSender.send(simpleMailMessage);
            return new NotificationDetailsDTO().firstName(accountDetails.getFirstName()).notificationMessage(simpleMailMessage.getText()).availableBalance(String.valueOf(accountDetails.getAccountBalance()));
        } catch (Exception e) {
            log.error("Notification cant send, Please check error logs...");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Notification Service Failed due to: " + e.getMessage());
        }
    }

    /**
     * prepareTranjactionMessage for email notification body
     *
     * @param accountDetails
     * @param tranjactionDetails
     * @return
     */
    private SimpleMailMessage prepareTranjactionMessage(AccountDetails accountDetails, TranjactionDetails tranjactionDetails) {
        String subject = (tranjactionDetails.getTranjactionType().isEmpty()) ? "Account Notification" : " Tranjaction Notification";
        String messageBody = (tranjactionDetails.getTranjactionType().isEmpty()) ? "Account created with :" + accountDetails.getAccountId() : tranjactionDetails.getTranjactionType() + " Tranjaction Completed with Id :" + tranjactionDetails.getTranjactionId();
        SimpleMailMessage notificationMessage = new SimpleMailMessage();
        notificationMessage.setFrom(fromMailId);
        notificationMessage.setTo(accountDetails.getEmailId());
        notificationMessage.setSubject(tranjactionSubject);
        notificationMessage.setText(messageBody);
        return notificationMessage;
    }

    /**
     * prepareAccCreatedMessage notification body
     *
     * @param accountDetails
     * @return
     */
    private SimpleMailMessage prepareAccCreatedMessage(AccountDetails accountDetails) {
        String subject = "Account Creation Notification";
        String messageBody = "Account created with Account Id :" + accountDetails.getAccountId();
        SimpleMailMessage notificationMessage = new SimpleMailMessage();
        notificationMessage.setFrom(fromMailId);
        notificationMessage.setTo(accountDetails.getEmailId());
        notificationMessage.setSubject(accCreatedSubject);
        notificationMessage.setText(messageBody);
        return notificationMessage;
    }
}