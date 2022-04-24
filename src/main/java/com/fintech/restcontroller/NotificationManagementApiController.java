package com.fintech.restcontroller;

import com.fintech.openapi.api.NotificationManagementApi;
import com.fintech.openapi.model.NotificationDetailsDTO;
import com.fintech.service.NotificationMicroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationManagementApiController implements NotificationManagementApi {
    private final NotificationMicroService notificationMicroService;

    @Override
    public ResponseEntity<List<NotificationDetailsDTO>> getAccountBalance(String accNumber) {
        return null;
    }
}