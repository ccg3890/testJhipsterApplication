package com.atensys.service;

import com.atensys.service.dto.ArchetypeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.atensys.domain.Archetype}.
 */
public interface ArchetypeService {
    /**
     * Save a archetype.
     *
     * @param archetypeDTO the entity to save.
     * @return the persisted entity.
     */
    ArchetypeDTO save(ArchetypeDTO archetypeDTO);

    /**
     * Updates a archetype.
     *
     * @param archetypeDTO the entity to update.
     * @return the persisted entity.
     */
    ArchetypeDTO update(ArchetypeDTO archetypeDTO);

    /**
     * Partially updates a archetype.
     *
     * @param archetypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArchetypeDTO> partialUpdate(ArchetypeDTO archetypeDTO);

    /**
     * Get all the archetypes.
     *
     * @return the list of entities.
     */
    List<ArchetypeDTO> findAll();

    /**
     * Get all the archetypes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArchetypeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" archetype.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArchetypeDTO> findOne(Long id);

    /**
     * Delete the "id" archetype.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
