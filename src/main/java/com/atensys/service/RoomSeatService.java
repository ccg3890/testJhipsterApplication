package com.atensys.service;

import com.atensys.service.dto.RoomSeatDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.RoomSeat}.
 */
public interface RoomSeatService {
    /**
     * Save a roomSeat.
     *
     * @param roomSeatDTO the entity to save.
     * @return the persisted entity.
     */
    RoomSeatDTO save(RoomSeatDTO roomSeatDTO);

    /**
     * Updates a roomSeat.
     *
     * @param roomSeatDTO the entity to update.
     * @return the persisted entity.
     */
    RoomSeatDTO update(RoomSeatDTO roomSeatDTO);

    /**
     * Partially updates a roomSeat.
     *
     * @param roomSeatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomSeatDTO> partialUpdate(RoomSeatDTO roomSeatDTO);

    /**
     * Get all the roomSeats.
     *
     * @return the list of entities.
     */
    List<RoomSeatDTO> findAll();

    /**
     * Get the "id" roomSeat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomSeatDTO> findOne(Long id);

    /**
     * Delete the "id" roomSeat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
