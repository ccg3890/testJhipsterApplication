package com.atensys.service.mapper;

import com.atensys.domain.Reservation;
import com.atensys.domain.ReservationTarget;
import com.atensys.domain.Resource;
import com.atensys.service.dto.ReservationDTO;
import com.atensys.service.dto.ReservationTargetDTO;
import com.atensys.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReservationTarget} and its DTO {@link ReservationTargetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationTargetMapper extends EntityMapper<ReservationTargetDTO, ReservationTarget> {
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    ReservationTargetDTO toDto(ReservationTarget s);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
