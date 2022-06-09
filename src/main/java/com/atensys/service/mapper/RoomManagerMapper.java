package com.atensys.service.mapper;

import com.atensys.domain.Room;
import com.atensys.domain.RoomManager;
import com.atensys.service.dto.RoomDTO;
import com.atensys.service.dto.RoomManagerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoomManager} and its DTO {@link RoomManagerDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomManagerMapper extends EntityMapper<RoomManagerDTO, RoomManager> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    RoomManagerDTO toDto(RoomManager s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);
}
