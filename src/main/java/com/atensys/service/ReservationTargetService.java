package com.atensys.service;

import com.atensys.service.dto.ReservationTargetDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.ReservationTarget}.
 */
public interface ReservationTargetService {
    /**
     * Save a reservationTarget.
     *
     * @param reservationTargetDTO the entity to save.
     * @return the persisted entity.
     */
    ReservationTargetDTO save(ReservationTargetDTO reservationTargetDTO);

    /**
     * Updates a reservationTarget.
     *
     * @param reservationTargetDTO the entity to update.
     * @return the persisted entity.
     */
    ReservationTargetDTO update(ReservationTargetDTO reservationTargetDTO);

    /**
     * Partially updates a reservationTarget.
     *
     * @param reservationTargetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReservationTargetDTO> partialUpdate(ReservationTargetDTO reservationTargetDTO);

    /**
     * Get all the reservationTargets.
     *
     * @return the list of entities.
     */
    List<ReservationTargetDTO> findAll();

    /**
     * Get the "id" reservationTarget.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReservationTargetDTO> findOne(Long id);

    /**
     * Delete the "id" reservationTarget.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
