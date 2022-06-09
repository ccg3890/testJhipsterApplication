package com.atensys.service.mapper;

import com.atensys.domain.ReservationTarget;
import com.atensys.domain.ReservedRoomSeat;
import com.atensys.service.dto.ReservationTargetDTO;
import com.atensys.service.dto.ReservedRoomSeatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReservedRoomSeat} and its DTO {@link ReservedRoomSeatDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservedRoomSeatMapper extends EntityMapper<ReservedRoomSeatDTO, ReservedRoomSeat> {
    @Mapping(target = "reservationTarget", source = "reservationTarget", qualifiedByName = "reservationTargetId")
    ReservedRoomSeatDTO toDto(ReservedRoomSeat s);

    @Named("reservationTargetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationTargetDTO toDtoReservationTargetId(ReservationTarget reservationTarget);
}
