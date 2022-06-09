package com.atensys.service.mapper;

import com.atensys.domain.Shape;
import com.atensys.domain.ShapeAsset;
import com.atensys.service.dto.ShapeAssetDTO;
import com.atensys.service.dto.ShapeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShapeAsset} and its DTO {@link ShapeAssetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShapeAssetMapper extends EntityMapper<ShapeAssetDTO, ShapeAsset> {
    @Mapping(target = "shape", source = "shape", qualifiedByName = "shapeId")
    ShapeAssetDTO toDto(ShapeAsset s);

    @Named("shapeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShapeDTO toDtoShapeId(Shape shape);
}
