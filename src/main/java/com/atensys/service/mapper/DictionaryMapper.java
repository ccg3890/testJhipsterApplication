package com.atensys.service.mapper;

import com.atensys.domain.Dictionary;
import com.atensys.service.dto.DictionaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dictionary} and its DTO {@link DictionaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface DictionaryMapper extends EntityMapper<DictionaryDTO, Dictionary> {}
