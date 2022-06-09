package com.atensys.service.mapper;

import com.atensys.domain.Seat;
import com.atensys.service.dto.SeatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seat} and its DTO {@link SeatDTO}.
 */
@Mapper(componentModel = "spring")
public interface SeatMapper extends EntityMapper<SeatDTO, Seat> {}
