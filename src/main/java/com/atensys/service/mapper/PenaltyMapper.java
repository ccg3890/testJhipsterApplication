package com.atensys.service.mapper;

import com.atensys.domain.Penalty;
import com.atensys.domain.UserInfo;
import com.atensys.service.dto.PenaltyDTO;
import com.atensys.service.dto.UserInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Penalty} and its DTO {@link PenaltyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PenaltyMapper extends EntityMapper<PenaltyDTO, Penalty> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "userInfoId")
    PenaltyDTO toDto(Penalty s);

    @Named("userInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserInfoDTO toDtoUserInfoId(UserInfo userInfo);
}
