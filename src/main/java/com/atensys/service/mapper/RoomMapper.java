package com.atensys.service.mapper;

import com.atensys.domain.Fileinfo;
import com.atensys.domain.Room;
import com.atensys.service.dto.FileinfoDTO;
import com.atensys.service.dto.RoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Room} and its DTO {@link RoomDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {
    @Mapping(target = "image", source = "image", qualifiedByName = "fileinfoId")
    RoomDTO toDto(Room s);

    @Named("fileinfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileinfoDTO toDtoFileinfoId(Fileinfo fileinfo);
}
