package com.fintech.mapper;

import com.fintech.entity.TranjactionDetails;
import com.fintech.openapi.model.TranjactionDetailsDTO;
import com.fintech.utils.ValidateUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TranjactionDetailsMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "tranjactionType", source = "tranjactionType")
    @Mapping(target = "tranjactionAmount", source = "tranjactionAmount")
    @Mapping(target = "tranjactionDate", source = "tranjactionDate")
    @Mapping(target = "tranjactionRemark", source = "tranjactionRemark")
    @Mapping(target = "accountId", source = "accountId")
    public abstract TranjactionDetails toTranjactionDetailsRequestBody(TranjactionDetailsDTO tranjactionDetailsDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "tranjactionId", source = "tranjactionId")
    @Mapping(target = "tranjactionType", source = "tranjactionType")
    @Mapping(target = "tranjactionAmount", source = "tranjactionAmount")
    @Mapping(target = "tranjactionDate", source = "tranjactionDate")
    @Mapping(target = "tranjactionRemark", source = "tranjactionRemark")
    @Mapping(target = "accountId", source = "accountId")
    public abstract TranjactionDetailsDTO toTranjactionDetailsDTO(TranjactionDetails tranjactionDetails);

}