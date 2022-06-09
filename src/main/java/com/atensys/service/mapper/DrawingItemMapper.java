package com.atensys.service.mapper;

import com.atensys.domain.Drawing;
import com.atensys.domain.DrawingItem;
import com.atensys.domain.Resource;
import com.atensys.domain.ShapeAsset;
import com.atensys.service.dto.DrawingDTO;
import com.atensys.service.dto.DrawingItemDTO;
import com.atensys.service.dto.ResourceDTO;
import com.atensys.service.dto.ShapeAssetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DrawingItem} and its DTO {@link DrawingItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface DrawingItemMapper extends EntityMapper<DrawingItemDTO, DrawingItem> {
    @Mapping(target = "shapeAsset", source = "shapeAsset", qualifiedByName = "shapeAssetId")
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    @Mapping(target = "drawing", source = "drawing", qualifiedByName = "drawingId")
    DrawingItemDTO toDto(DrawingItem s);

    @Named("shapeAssetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShapeAssetDTO toDtoShapeAssetId(ShapeAsset shapeAsset);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);

    @Named("drawingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DrawingDTO toDtoDrawingId(Drawing drawing);
}
