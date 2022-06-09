package com.atensys.service.mapper;

import com.atensys.domain.Attendee;
import com.atensys.domain.ReservationTarget;
import com.atensys.service.dto.AttendeeDTO;
import com.atensys.service.dto.ReservationTargetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendee} and its DTO {@link AttendeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendeeMapper extends EntityMapper<AttendeeDTO, Attendee> {
    @Mapping(target = "reservationTarget", source = "reservationTarget", qualifiedByName = "reservationTargetId")
    AttendeeDTO toDto(Attendee s);

    @Named("reservationTargetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationTargetDTO toDtoReservationTargetId(ReservationTarget reservationTarget);
}
