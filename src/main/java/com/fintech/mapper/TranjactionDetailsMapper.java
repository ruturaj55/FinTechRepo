package com.fintech.mapper;

import com.fintech.entity.TranjactionDetails;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import com.fintech.utils.ValidateUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface TranjactionDetailsMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "tranjactionId", source = "tranjactionId")
    @Mapping(target = "tranjactionType", source = "tranjactionType")
    @Mapping(target = "tranjactionAmount", source = "tranjactionAmount")
    //@Mapping(target = "tranjactionDateTime", source = "java(mapTranjactionDateTimeToLocalDateTime(tranjactionDetailsDTO))")
    @Mapping(target = "tranjactionRemark", source = "tranjactionRemark")
    public abstract TranjactionDetails
    toTranjactionDetailsRequestBody(TranjactionDetailsDTO tranjactionDetailsDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "tranjactionId", source = "tranjactionId")
    @Mapping(target = "tranjactionType", source = "tranjactionType")
    @Mapping(target = "tranjactionAmount", source = "tranjactionAmount")
   // @Mapping(target = "tranjactionDateTime", source = "java(mapTranjactionDateTimeToOffsetDateTime(tranjactionDetails))")
    @Mapping(target = "tranjactionRemark", source = "tranjactionRemark")
    //@Mapping(target = "accountId", source = "java(mapAccountIdToString(tranjactionDetails))")
    public abstract TranjactionDetailsDTO toTranjactionDetailsDTO(TranjactionDetails tranjactionDetails);


    default String mapAccountIdToString(TranjactionDetails tranjactionDetails) {
        String accountId = "";
        if (ValidateUtils.isNotNullOrEmpty(tranjactionDetails.getAccountId())) {
            accountId = String.valueOf(tranjactionDetails.getAccountId());
        }
        return accountId;
    }

    default LocalDateTime mapTranjactionDateTimeToLocalDateTime(TranjactionDetailsDTO tranjactionDetailsDTO) {
        LocalDateTime tranjactionDateTime = LocalDateTime.now();
        if (ValidateUtils.isNotNullOrEmpty(tranjactionDetailsDTO.getAccountId())) {
            ZoneOffset zoneOffSet = OffsetDateTime.now().getOffset();
            System.out.println("ZoneOffset is: " + zoneOffSet.toString());
            tranjactionDateTime = tranjactionDetailsDTO.getTranjactionDateTime().toLocalDateTime();
        }

        return tranjactionDateTime;
    }

    default OffsetDateTime mapTranjactionDateTimeToOffsetDateTime(TranjactionDetails tranjactionDetails) {
        OffsetDateTime tranjactionDateTime = OffsetDateTime.now();
        if (ValidateUtils.isNotNullOrEmpty(tranjactionDetails.getTranjactionDateTime())) {
            ZoneOffset zoneOffSet = OffsetDateTime.now().getOffset();
            System.out.println("ZoneOffset is: " + zoneOffSet.toString());
            tranjactionDateTime = tranjactionDetails.getTranjactionDateTime().atOffset(zoneOffSet);

        }
        return tranjactionDateTime;
    }

}