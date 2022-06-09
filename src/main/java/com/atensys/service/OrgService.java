package com.atensys.service;

import com.atensys.service.dto.OrgDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Org}.
 */
public interface OrgService {
    /**
     * Save a org.
     *
     * @param orgDTO the entity to save.
     * @return the persisted entity.
     */
    OrgDTO save(OrgDTO orgDTO);

    /**
     * Updates a org.
     *
     * @param orgDTO the entity to update.
     * @return the persisted entity.
     */
    OrgDTO update(OrgDTO orgDTO);

    /**
     * Partially updates a org.
     *
     * @param orgDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrgDTO> partialUpdate(OrgDTO orgDTO);

    /**
     * Get all the orgs.
     *
     * @return the list of entities.
     */
    List<OrgDTO> findAll();

    /**
     * Get the "id" org.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrgDTO> findOne(Long id);

    /**
     * Delete the "id" org.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
