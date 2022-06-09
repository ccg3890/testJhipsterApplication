package com.atensys.service.mapper;

import com.atensys.domain.Drawing;
import com.atensys.service.dto.DrawingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drawing} and its DTO {@link DrawingDTO}.
 */
@Mapper(componentModel = "spring")
public interface DrawingMapper extends EntityMapper<DrawingDTO, Drawing> {}
