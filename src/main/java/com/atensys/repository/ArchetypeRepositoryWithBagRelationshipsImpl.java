package com.atensys.repository;

import com.atensys.domain.Archetype;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ArchetypeRepositoryWithBagRelationshipsImpl implements ArchetypeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Archetype> fetchBagRelationships(Optional<Archetype> archetype) {
        return archetype.map(this::fetchDictionaries);
    }

    @Override
    public Page<Archetype> fetchBagRelationships(Page<Archetype> archetypes) {
        return new PageImpl<>(fetchBagRelationships(archetypes.getContent()), archetypes.getPageable(), archetypes.getTotalElements());
    }

    @Override
    public List<Archetype> fetchBagRelationships(List<Archetype> archetypes) {
        return Optional.of(archetypes).map(this::fetchDictionaries).orElse(Collections.emptyList());
    }

    Archetype fetchDictionaries(Archetype result) {
        return entityManager
            .createQuery(
                "select archetype from Archetype archetype left join fetch archetype.dictionaries where archetype is :archetype",
                Archetype.class
            )
            .setParameter("archetype", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Archetype> fetchDictionaries(List<Archetype> archetypes) {
        return entityManager
            .createQuery(
                "select distinct archetype from Archetype archetype left join fetch archetype.dictionaries where archetype in :archetypes",
                Archetype.class
            )
            .setParameter("archetypes", archetypes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
