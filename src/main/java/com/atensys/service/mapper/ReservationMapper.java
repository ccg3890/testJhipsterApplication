package com.atensys.service.mapper;

import com.atensys.domain.Recurrence;
import com.atensys.domain.Reservation;
import com.atensys.service.dto.RecurrenceDTO;
import com.atensys.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "recurrence", source = "recurrence", qualifiedByName = "recurrenceId")
    ReservationDTO toDto(Reservation s);

    @Named("recurrenceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecurrenceDTO toDtoRecurrenceId(Recurrence recurrence);
}
