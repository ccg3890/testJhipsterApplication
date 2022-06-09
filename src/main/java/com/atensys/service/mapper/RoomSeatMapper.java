package com.atensys.service.mapper;

import com.atensys.domain.Room;
import com.atensys.domain.RoomSeat;
import com.atensys.service.dto.RoomDTO;
import com.atensys.service.dto.RoomSeatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoomSeat} and its DTO {@link RoomSeatDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomSeatMapper extends EntityMapper<RoomSeatDTO, RoomSeat> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    RoomSeatDTO toDto(RoomSeat s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);
}
