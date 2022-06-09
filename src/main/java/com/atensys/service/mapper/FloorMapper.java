package com.atensys.service.mapper;

import com.atensys.domain.Drawing;
import com.atensys.domain.Fileinfo;
import com.atensys.domain.Floor;
import com.atensys.domain.Office;
import com.atensys.service.dto.DrawingDTO;
import com.atensys.service.dto.FileinfoDTO;
import com.atensys.service.dto.FloorDTO;
import com.atensys.service.dto.OfficeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Floor} and its DTO {@link FloorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {
    @Mapping(target = "webImg", source = "webImg", qualifiedByName = "fileinfoId")
    @Mapping(target = "kioskImg", source = "kioskImg", qualifiedByName = "fileinfoId")
    @Mapping(target = "miniImg", source = "miniImg", qualifiedByName = "fileinfoId")
    @Mapping(target = "drawing", source = "drawing", qualifiedByName = "drawingId")
    @Mapping(target = "office", source = "office", qualifiedByName = "officeId")
    FloorDTO toDto(Floor s);

    @Named("fileinfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileinfoDTO toDtoFileinfoId(Fileinfo fileinfo);

    @Named("drawingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DrawingDTO toDtoDrawingId(Drawing drawing);

    @Named("officeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfficeDTO toDtoOfficeId(Office office);
}
