package com.atensys.service.mapper;

import com.atensys.domain.Shape;
import com.atensys.service.dto.ShapeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shape} and its DTO {@link ShapeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShapeMapper extends EntityMapper<ShapeDTO, Shape> {}
