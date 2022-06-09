package com.atensys.service.mapper;

import com.atensys.domain.Archetype;
import com.atensys.domain.Dictionary;
import com.atensys.service.dto.ArchetypeDTO;
import com.atensys.service.dto.DictionaryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Archetype} and its DTO {@link ArchetypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArchetypeMapper extends EntityMapper<ArchetypeDTO, Archetype> {
    @Mapping(target = "dictionaries", source = "dictionaries", qualifiedByName = "dictionaryIdSet")
    ArchetypeDTO toDto(Archetype s);

    @Mapping(target = "removeDictionary", ignore = true)
    Archetype toEntity(ArchetypeDTO archetypeDTO);

    @Named("dictionaryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DictionaryDTO toDtoDictionaryId(Dictionary dictionary);

    @Named("dictionaryIdSet")
    default Set<DictionaryDTO> toDtoDictionaryIdSet(Set<Dictionary> dictionary) {
        return dictionary.stream().map(this::toDtoDictionaryId).collect(Collectors.toSet());
    }
}
