package com.atensys.service;

import com.atensys.service.dto.ReservedDateDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.ReservedDate}.
 */
public interface ReservedDateService {
    /**
     * Save a reservedDate.
     *
     * @param reservedDateDTO the entity to save.
     * @return the persisted entity.
     */
    ReservedDateDTO save(ReservedDateDTO reservedDateDTO);

    /**
     * Updates a reservedDate.
     *
     * @param reservedDateDTO the entity to update.
     * @return the persisted entity.
     */
    ReservedDateDTO update(ReservedDateDTO reservedDateDTO);

    /**
     * Partially updates a reservedDate.
     *
     * @param reservedDateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReservedDateDTO> partialUpdate(ReservedDateDTO reservedDateDTO);

    /**
     * Get all the reservedDates.
     *
     * @return the list of entities.
     */
    List<ReservedDateDTO> findAll();

    /**
     * Get the "id" reservedDate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReservedDateDTO> findOne(Long id);

    /**
     * Delete the "id" reservedDate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
