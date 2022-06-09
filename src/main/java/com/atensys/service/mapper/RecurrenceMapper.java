package com.atensys.service.mapper;

import com.atensys.domain.Recurrence;
import com.atensys.service.dto.RecurrenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recurrence} and its DTO {@link RecurrenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecurrenceMapper extends EntityMapper<RecurrenceDTO, Recurrence> {}
