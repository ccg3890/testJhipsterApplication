package com.atensys.service;

import com.atensys.service.dto.FloorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Floor}.
 */
public interface FloorService {
    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    FloorDTO save(FloorDTO floorDTO);

    /**
     * Updates a floor.
     *
     * @param floorDTO the entity to update.
     * @return the persisted entity.
     */
    FloorDTO update(FloorDTO floorDTO);

    /**
     * Partially updates a floor.
     *
     * @param floorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FloorDTO> partialUpdate(FloorDTO floorDTO);

    /**
     * Get all the floors.
     *
     * @return the list of entities.
     */
    List<FloorDTO> findAll();

    /**
     * Get the "id" floor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FloorDTO> findOne(Long id);

    /**
     * Delete the "id" floor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
