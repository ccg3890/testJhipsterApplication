package com.atensys.service.mapper;

import com.atensys.domain.AttributeRoom;
import com.atensys.domain.Room;
import com.atensys.service.dto.AttributeRoomDTO;
import com.atensys.service.dto.RoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeRoom} and its DTO {@link AttributeRoomDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeRoomMapper extends EntityMapper<AttributeRoomDTO, AttributeRoom> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    AttributeRoomDTO toDto(AttributeRoom s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);
}
