package com.atensys.service.mapper;

import com.atensys.domain.Reservation;
import com.atensys.domain.ReservedDate;
import com.atensys.service.dto.ReservationDTO;
import com.atensys.service.dto.ReservedDateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReservedDate} and its DTO {@link ReservedDateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservedDateMapper extends EntityMapper<ReservedDateDTO, ReservedDate> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    ReservedDateDTO toDto(ReservedDate s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
