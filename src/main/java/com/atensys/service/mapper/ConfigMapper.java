package com.atensys.service.mapper;

import com.atensys.domain.Config;
import com.atensys.service.dto.ConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Config} and its DTO {@link ConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfigMapper extends EntityMapper<ConfigDTO, Config> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "configId")
    ConfigDTO toDto(Config s);

    @Named("configId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConfigDTO toDtoConfigId(Config config);
}
