package com.atensys.service.mapper;

import com.atensys.domain.Company;
import com.atensys.domain.Org;
import com.atensys.service.dto.CompanyDTO;
import com.atensys.service.dto.OrgDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Org} and its DTO {@link OrgDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrgMapper extends EntityMapper<OrgDTO, Org> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "orgId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    OrgDTO toDto(Org s);

    @Named("orgId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrgDTO toDtoOrgId(Org org);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
