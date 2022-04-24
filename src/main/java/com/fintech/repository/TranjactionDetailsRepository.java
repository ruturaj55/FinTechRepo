package com.fintech.repository;

import com.fintech.entity.TranjactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranjactionDetailsRepository extends JpaRepository<TranjactionDetails, Long> {
}