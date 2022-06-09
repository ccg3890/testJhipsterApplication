package com.atensys.service.mapper;

import com.atensys.domain.Company;
import com.atensys.domain.Office;
import com.atensys.service.dto.CompanyDTO;
import com.atensys.service.dto.OfficeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Office} and its DTO {@link OfficeDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfficeMapper extends EntityMapper<OfficeDTO, Office> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    OfficeDTO toDto(Office s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
