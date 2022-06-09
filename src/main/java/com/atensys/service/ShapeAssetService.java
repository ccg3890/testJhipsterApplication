package com.atensys.service;

import com.atensys.service.dto.ShapeAssetDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.ShapeAsset}.
 */
public interface ShapeAssetService {
    /**
     * Save a shapeAsset.
     *
     * @param shapeAssetDTO the entity to save.
     * @return the persisted entity.
     */
    ShapeAssetDTO save(ShapeAssetDTO shapeAssetDTO);

    /**
     * Updates a shapeAsset.
     *
     * @param shapeAssetDTO the entity to update.
     * @return the persisted entity.
     */
    ShapeAssetDTO update(ShapeAssetDTO shapeAssetDTO);

    /**
     * Partially updates a shapeAsset.
     *
     * @param shapeAssetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShapeAssetDTO> partialUpdate(ShapeAssetDTO shapeAssetDTO);

    /**
     * Get all the shapeAssets.
     *
     * @return the list of entities.
     */
    List<ShapeAssetDTO> findAll();

    /**
     * Get the "id" shapeAsset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShapeAssetDTO> findOne(Long id);

    /**
     * Delete the "id" shapeAsset.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
