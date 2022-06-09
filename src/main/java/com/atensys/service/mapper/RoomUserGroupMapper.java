package com.atensys.service.mapper;

import com.atensys.domain.Room;
import com.atensys.domain.RoomUserGroup;
import com.atensys.service.dto.RoomDTO;
import com.atensys.service.dto.RoomUserGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoomUserGroup} and its DTO {@link RoomUserGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomUserGroupMapper extends EntityMapper<RoomUserGroupDTO, RoomUserGroup> {
    @Mapping(target = "room", source = "room", qualifiedByName = "roomId")
    RoomUserGroupDTO toDto(RoomUserGroup s);

    @Named("roomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoomDTO toDtoRoomId(Room room);
}
