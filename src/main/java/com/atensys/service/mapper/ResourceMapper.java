package com.atensys.service.mapper;

import com.atensys.domain.Floor;
import com.atensys.domain.Resource;
import com.atensys.service.dto.FloorDTO;
import com.atensys.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {
    @Mapping(target = "floor", source = "floor", qualifiedByName = "floorId")
    ResourceDTO toDto(Resource s);

    @Named("floorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FloorDTO toDtoFloorId(Floor floor);
}
