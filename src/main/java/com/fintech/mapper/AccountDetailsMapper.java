package com.fintech.mapper;

import com.fintech.entity.AccountDetails;
import com.fintech.openapi.model.AccountDetailsDTO;
import com.fintech.utils.ValidateUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AccountDetailsMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "accountBalance", source = "initialAccountBalance")
    @Mapping(target = "emailId", source = "emailId")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "remark", source = "remark")
    public abstract AccountDetails toAccountDetailsRequestBody(AccountDetailsDTO accountDetailsDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "accountId", expression = "java(mapAccountId(accountDetails))")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "emailId", source = "emailId")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "initialAccountBalance", source = "accountBalance")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "remark", source = "remark")
    public abstract AccountDetailsDTO toAccountDetailsDTORequestBody(AccountDetails accountDetails);

    String mapAccountId(AccountDetails accountDetails) {
        String accountId = "";
        if (ValidateUtils.isNotNullOrEmpty(accountDetails.getAccountId())) {
            accountId = String.valueOf(accountDetails.getAccountId());
        }
        return accountId;
    }

}