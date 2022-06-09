package com.atensys.service.mapper;

import com.atensys.domain.Rank;
import com.atensys.service.dto.RankDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rank} and its DTO {@link RankDTO}.
 */
@Mapper(componentModel = "spring")
public interface RankMapper extends EntityMapper<RankDTO, Rank> {}
