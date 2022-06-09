package com.atensys.service.mapper;

import com.atensys.domain.AttributeCompany;
import com.atensys.domain.Company;
import com.atensys.service.dto.AttributeCompanyDTO;
import com.atensys.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeCompany} and its DTO {@link AttributeCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeCompanyMapper extends EntityMapper<AttributeCompanyDTO, AttributeCompany> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    AttributeCompanyDTO toDto(AttributeCompany s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
