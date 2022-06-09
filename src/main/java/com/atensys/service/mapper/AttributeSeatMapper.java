package com.atensys.service.mapper;

import com.atensys.domain.AttributeSeat;
import com.atensys.domain.Seat;
import com.atensys.service.dto.AttributeSeatDTO;
import com.atensys.service.dto.SeatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeSeat} and its DTO {@link AttributeSeatDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeSeatMapper extends EntityMapper<AttributeSeatDTO, AttributeSeat> {
    @Mapping(target = "seat", source = "seat", qualifiedByName = "seatId")
    AttributeSeatDTO toDto(AttributeSeat s);

    @Named("seatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SeatDTO toDtoSeatId(Seat seat);
}
