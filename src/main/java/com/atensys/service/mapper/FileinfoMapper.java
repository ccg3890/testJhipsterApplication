package com.atensys.service.mapper;

import com.atensys.domain.Fileinfo;
import com.atensys.service.dto.FileinfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fileinfo} and its DTO {@link FileinfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileinfoMapper extends EntityMapper<FileinfoDTO, Fileinfo> {}
