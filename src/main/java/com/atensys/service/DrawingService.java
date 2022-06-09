package com.atensys.service;

import com.atensys.service.dto.DrawingDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Drawing}.
 */
public interface DrawingService {
    /**
     * Save a drawing.
     *
     * @param drawingDTO the entity to save.
     * @return the persisted entity.
     */
    DrawingDTO save(DrawingDTO drawingDTO);

    /**
     * Updates a drawing.
     *
     * @param drawingDTO the entity to update.
     * @return the persisted entity.
     */
    DrawingDTO update(DrawingDTO drawingDTO);

    /**
     * Partially updates a drawing.
     *
     * @param drawingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DrawingDTO> partialUpdate(DrawingDTO drawingDTO);

    /**
     * Get all the drawings.
     *
     * @return the list of entities.
     */
    List<DrawingDTO> findAll();

    /**
     * Get the "id" drawing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DrawingDTO> findOne(Long id);

    /**
     * Delete the "id" drawing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
