package com.atensys.service;

import com.atensys.service.dto.AttributeSeatDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.AttributeSeat}.
 */
public interface AttributeSeatService {
    /**
     * Save a attributeSeat.
     *
     * @param attributeSeatDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeSeatDTO save(AttributeSeatDTO attributeSeatDTO);

    /**
     * Updates a attributeSeat.
     *
     * @param attributeSeatDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeSeatDTO update(AttributeSeatDTO attributeSeatDTO);

    /**
     * Partially updates a attributeSeat.
     *
     * @param attributeSeatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeSeatDTO> partialUpdate(AttributeSeatDTO attributeSeatDTO);

    /**
     * Get all the attributeSeats.
     *
     * @return the list of entities.
     */
    List<AttributeSeatDTO> findAll();

    /**
     * Get the "id" attributeSeat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeSeatDTO> findOne(Long id);

    /**
     * Delete the "id" attributeSeat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
