package com.atensys.service.mapper;

import com.atensys.domain.Auth;
import com.atensys.domain.Company;
import com.atensys.domain.Fileinfo;
import com.atensys.domain.Org;
import com.atensys.domain.Rank;
import com.atensys.domain.UserInfo;
import com.atensys.service.dto.AuthDTO;
import com.atensys.service.dto.CompanyDTO;
import com.atensys.service.dto.FileinfoDTO;
import com.atensys.service.dto.OrgDTO;
import com.atensys.service.dto.RankDTO;
import com.atensys.service.dto.UserInfoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    @Mapping(target = "org", source = "org", qualifiedByName = "orgId")
    @Mapping(target = "rank", source = "rank", qualifiedByName = "rankId")
    @Mapping(target = "file", source = "file", qualifiedByName = "fileinfoId")
    @Mapping(target = "auths", source = "auths", qualifiedByName = "authIdSet")
    UserInfoDTO toDto(UserInfo s);

    @Mapping(target = "removeAuth", ignore = true)
    UserInfo toEntity(UserInfoDTO userInfoDTO);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);

    @Named("orgId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrgDTO toDtoOrgId(Org org);

    @Named("rankId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RankDTO toDtoRankId(Rank rank);

    @Named("fileinfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileinfoDTO toDtoFileinfoId(Fileinfo fileinfo);

    @Named("authId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthDTO toDtoAuthId(Auth auth);

    @Named("authIdSet")
    default Set<AuthDTO> toDtoAuthIdSet(Set<Auth> auth) {
        return auth.stream().map(this::toDtoAuthId).collect(Collectors.toSet());
    }
}
