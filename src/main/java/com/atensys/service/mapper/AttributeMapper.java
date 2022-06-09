package com.atensys.service.mapper;

import com.atensys.domain.Attribute;
import com.atensys.domain.Dictionary;
import com.atensys.service.dto.AttributeDTO;
import com.atensys.service.dto.DictionaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attribute} and its DTO {@link AttributeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeMapper extends EntityMapper<AttributeDTO, Attribute> {
    @Mapping(target = "dictionary", source = "dictionary", qualifiedByName = "dictionaryId")
    AttributeDTO toDto(Attribute s);

    @Named("dictionaryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DictionaryDTO toDtoDictionaryId(Dictionary dictionary);
}
