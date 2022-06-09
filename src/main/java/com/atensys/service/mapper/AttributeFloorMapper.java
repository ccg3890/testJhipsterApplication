package com.atensys.service.mapper;

import com.atensys.domain.AttributeFloor;
import com.atensys.domain.Floor;
import com.atensys.service.dto.AttributeFloorDTO;
import com.atensys.service.dto.FloorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeFloor} and its DTO {@link AttributeFloorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeFloorMapper extends EntityMapper<AttributeFloorDTO, AttributeFloor> {
    @Mapping(target = "floor", source = "floor", qualifiedByName = "floorId")
    AttributeFloorDTO toDto(AttributeFloor s);

    @Named("floorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FloorDTO toDtoFloorId(Floor floor);
}
