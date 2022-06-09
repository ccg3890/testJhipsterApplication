package com.atensys.service.mapper;

import com.atensys.domain.Auth;
import com.atensys.domain.Menu;
import com.atensys.service.dto.AuthDTO;
import com.atensys.service.dto.MenuDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Auth} and its DTO {@link AuthDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthMapper extends EntityMapper<AuthDTO, Auth> {
    @Mapping(target = "menus", source = "menus", qualifiedByName = "menuIdSet")
    AuthDTO toDto(Auth s);

    @Mapping(target = "removeMenu", ignore = true)
    Auth toEntity(AuthDTO authDTO);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);

    @Named("menuIdSet")
    default Set<MenuDTO> toDtoMenuIdSet(Set<Menu> menu) {
        return menu.stream().map(this::toDtoMenuId).collect(Collectors.toSet());
    }
}
