package com.atensys.service.mapper;

import com.atensys.domain.AttributeOffice;
import com.atensys.domain.Office;
import com.atensys.service.dto.AttributeOfficeDTO;
import com.atensys.service.dto.OfficeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeOffice} and its DTO {@link AttributeOfficeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeOfficeMapper extends EntityMapper<AttributeOfficeDTO, AttributeOffice> {
    @Mapping(target = "office", source = "office", qualifiedByName = "officeId")
    AttributeOfficeDTO toDto(AttributeOffice s);

    @Named("officeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfficeDTO toDtoOfficeId(Office office);
}
