package com.atensys.service;

import com.atensys.service.dto.AttributeRoomDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.AttributeRoom}.
 */
public interface AttributeRoomService {
    /**
     * Save a attributeRoom.
     *
     * @param attributeRoomDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeRoomDTO save(AttributeRoomDTO attributeRoomDTO);

    /**
     * Updates a attributeRoom.
     *
     * @param attributeRoomDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeRoomDTO update(AttributeRoomDTO attributeRoomDTO);

    /**
     * Partially updates a attributeRoom.
     *
     * @param attributeRoomDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeRoomDTO> partialUpdate(AttributeRoomDTO attributeRoomDTO);

    /**
     * Get all the attributeRooms.
     *
     * @return the list of entities.
     */
    List<AttributeRoomDTO> findAll();

    /**
     * Get the "id" attributeRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeRoomDTO> findOne(Long id);

    /**
     * Delete the "id" attributeRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
