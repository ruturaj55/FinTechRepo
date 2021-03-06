package com.fintech.repository;

import com.fintech.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails,Long> {
    @Query
    AccountDetails findByEmailId(String emailId);

}