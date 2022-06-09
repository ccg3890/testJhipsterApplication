package com.atensys.service;

import com.atensys.service.dto.DrawingItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.DrawingItem}.
 */
public interface DrawingItemService {
    /**
     * Save a drawingItem.
     *
     * @param drawingItemDTO the entity to save.
     * @return the persisted entity.
     */
    DrawingItemDTO save(DrawingItemDTO drawingItemDTO);

    /**
     * Updates a drawingItem.
     *
     * @param drawingItemDTO the entity to update.
     * @return the persisted entity.
     */
    DrawingItemDTO update(DrawingItemDTO drawingItemDTO);

    /**
     * Partially updates a drawingItem.
     *
     * @param drawingItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DrawingItemDTO> partialUpdate(DrawingItemDTO drawingItemDTO);

    /**
     * Get all the drawingItems.
     *
     * @return the list of entities.
     */
    List<DrawingItemDTO> findAll();

    /**
     * Get the "id" drawingItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DrawingItemDTO> findOne(Long id);

    /**
     * Delete the "id" drawingItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
