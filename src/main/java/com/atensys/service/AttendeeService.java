package com.atensys.service;

import com.atensys.service.dto.AttendeeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Attendee}.
 */
public interface AttendeeService {
    /**
     * Save a attendee.
     *
     * @param attendeeDTO the entity to save.
     * @return the persisted entity.
     */
    AttendeeDTO save(AttendeeDTO attendeeDTO);

    /**
     * Updates a attendee.
     *
     * @param attendeeDTO the entity to update.
     * @return the persisted entity.
     */
    AttendeeDTO update(AttendeeDTO attendeeDTO);

    /**
     * Partially updates a attendee.
     *
     * @param attendeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttendeeDTO> partialUpdate(AttendeeDTO attendeeDTO);

    /**
     * Get all the attendees.
     *
     * @return the list of entities.
     */
    List<AttendeeDTO> findAll();

    /**
     * Get the "id" attendee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendeeDTO> findOne(Long id);

    /**
     * Delete the "id" attendee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
