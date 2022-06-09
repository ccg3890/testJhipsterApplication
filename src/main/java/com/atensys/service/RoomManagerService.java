package com.atensys.service;

import com.atensys.service.dto.RoomManagerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.RoomManager}.
 */
public interface RoomManagerService {
    /**
     * Save a roomManager.
     *
     * @param roomManagerDTO the entity to save.
     * @return the persisted entity.
     */
    RoomManagerDTO save(RoomManagerDTO roomManagerDTO);

    /**
     * Updates a roomManager.
     *
     * @param roomManagerDTO the entity to update.
     * @return the persisted entity.
     */
    RoomManagerDTO update(RoomManagerDTO roomManagerDTO);

    /**
     * Partially updates a roomManager.
     *
     * @param roomManagerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomManagerDTO> partialUpdate(RoomManagerDTO roomManagerDTO);

    /**
     * Get all the roomManagers.
     *
     * @return the list of entities.
     */
    List<RoomManagerDTO> findAll();

    /**
     * Get the "id" roomManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomManagerDTO> findOne(Long id);

    /**
     * Delete the "id" roomManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
