package com.fintech.restcontroller;

import com.fintech.openapi.api.TranjactionManagementApi;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import com.fintech.service.TranjactionMicroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranjactionManagementApiController implements TranjactionManagementApi {
    private final TranjactionMicroService tranjactionMicroService;

    @Override
    public ResponseEntity<TranjactionDetailsDTO> updateAccountBalance(TranjactionDetailsDTO tranjactionDetailsDTO) {
        return ResponseEntity.ok(tranjactionMicroService.newTranjaction(tranjactionDetailsDTO));
    }
}