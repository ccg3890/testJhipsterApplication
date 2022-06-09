package com.atensys.repository;

import com.atensys.domain.Archetype;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ArchetypeRepositoryWithBagRelationships {
    Optional<Archetype> fetchBagRelationships(Optional<Archetype> archetype);

    List<Archetype> fetchBagRelationships(List<Archetype> archetypes);

    Page<Archetype> fetchBagRelationships(Page<Archetype> archetypes);
}
