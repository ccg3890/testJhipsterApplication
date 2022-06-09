package com.atensys.service;

import com.atensys.service.dto.ReservedRoomSeatDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.ReservedRoomSeat}.
 */
public interface ReservedRoomSeatService {
    /**
     * Save a reservedRoomSeat.
     *
     * @param reservedRoomSeatDTO the entity to save.
     * @return the persisted entity.
     */
    ReservedRoomSeatDTO save(ReservedRoomSeatDTO reservedRoomSeatDTO);

    /**
     * Updates a reservedRoomSeat.
     *
     * @param reservedRoomSeatDTO the entity to update.
     * @return the persisted entity.
     */
    ReservedRoomSeatDTO update(ReservedRoomSeatDTO reservedRoomSeatDTO);

    /**
     * Partially updates a reservedRoomSeat.
     *
     * @param reservedRoomSeatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReservedRoomSeatDTO> partialUpdate(ReservedRoomSeatDTO reservedRoomSeatDTO);

    /**
     * Get all the reservedRoomSeats.
     *
     * @return the list of entities.
     */
    List<ReservedRoomSeatDTO> findAll();

    /**
     * Get the "id" reservedRoomSeat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReservedRoomSeatDTO> findOne(Long id);

    /**
     * Delete the "id" reservedRoomSeat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
