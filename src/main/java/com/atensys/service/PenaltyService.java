package com.atensys.service;

import com.atensys.service.dto.PenaltyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Penalty}.
 */
public interface PenaltyService {
    /**
     * Save a penalty.
     *
     * @param penaltyDTO the entity to save.
     * @return the persisted entity.
     */
    PenaltyDTO save(PenaltyDTO penaltyDTO);

    /**
     * Updates a penalty.
     *
     * @param penaltyDTO the entity to update.
     * @return the persisted entity.
     */
    PenaltyDTO update(PenaltyDTO penaltyDTO);

    /**
     * Partially updates a penalty.
     *
     * @param penaltyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PenaltyDTO> partialUpdate(PenaltyDTO penaltyDTO);

    /**
     * Get all the penalties.
     *
     * @return the list of entities.
     */
    List<PenaltyDTO> findAll();

    /**
     * Get the "id" penalty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PenaltyDTO> findOne(Long id);

    /**
     * Delete the "id" penalty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
