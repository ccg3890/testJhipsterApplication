package com.atensys.service;

import com.atensys.service.dto.ShapeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Shape}.
 */
public interface ShapeService {
    /**
     * Save a shape.
     *
     * @param shapeDTO the entity to save.
     * @return the persisted entity.
     */
    ShapeDTO save(ShapeDTO shapeDTO);

    /**
     * Updates a shape.
     *
     * @param shapeDTO the entity to update.
     * @return the persisted entity.
     */
    ShapeDTO update(ShapeDTO shapeDTO);

    /**
     * Partially updates a shape.
     *
     * @param shapeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShapeDTO> partialUpdate(ShapeDTO shapeDTO);

    /**
     * Get all the shapes.
     *
     * @return the list of entities.
     */
    List<ShapeDTO> findAll();

    /**
     * Get the "id" shape.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShapeDTO> findOne(Long id);

    /**
     * Delete the "id" shape.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
