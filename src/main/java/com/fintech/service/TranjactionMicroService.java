package com.fintech.service;

import com.fintech.entity.TranjactionDetails;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface TranjactionMicroService {

    TranjactionDetailsDTO newTranjaction(TranjactionDetailsDTO tranjactionDetailsDTO);
}