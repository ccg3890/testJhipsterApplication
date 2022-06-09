package com.atensys.service;

import com.atensys.service.dto.RecurrenceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Recurrence}.
 */
public interface RecurrenceService {
    /**
     * Save a recurrence.
     *
     * @param recurrenceDTO the entity to save.
     * @return the persisted entity.
     */
    RecurrenceDTO save(RecurrenceDTO recurrenceDTO);

    /**
     * Updates a recurrence.
     *
     * @param recurrenceDTO the entity to update.
     * @return the persisted entity.
     */
    RecurrenceDTO update(RecurrenceDTO recurrenceDTO);

    /**
     * Partially updates a recurrence.
     *
     * @param recurrenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecurrenceDTO> partialUpdate(RecurrenceDTO recurrenceDTO);

    /**
     * Get all the recurrences.
     *
     * @return the list of entities.
     */
    List<RecurrenceDTO> findAll();

    /**
     * Get the "id" recurrence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecurrenceDTO> findOne(Long id);

    /**
     * Delete the "id" recurrence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
