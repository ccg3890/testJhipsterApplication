package com.atensys.service;

import com.atensys.service.dto.AttributeFloorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.AttributeFloor}.
 */
public interface AttributeFloorService {
    /**
     * Save a attributeFloor.
     *
     * @param attributeFloorDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeFloorDTO save(AttributeFloorDTO attributeFloorDTO);

    /**
     * Updates a attributeFloor.
     *
     * @param attributeFloorDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeFloorDTO update(AttributeFloorDTO attributeFloorDTO);

    /**
     * Partially updates a attributeFloor.
     *
     * @param attributeFloorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeFloorDTO> partialUpdate(AttributeFloorDTO attributeFloorDTO);

    /**
     * Get all the attributeFloors.
     *
     * @return the list of entities.
     */
    List<AttributeFloorDTO> findAll();

    /**
     * Get the "id" attributeFloor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeFloorDTO> findOne(Long id);

    /**
     * Delete the "id" attributeFloor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
