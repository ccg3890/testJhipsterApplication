package com.atensys.service;

import com.atensys.service.dto.RoomUserGroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.RoomUserGroup}.
 */
public interface RoomUserGroupService {
    /**
     * Save a roomUserGroup.
     *
     * @param roomUserGroupDTO the entity to save.
     * @return the persisted entity.
     */
    RoomUserGroupDTO save(RoomUserGroupDTO roomUserGroupDTO);

    /**
     * Updates a roomUserGroup.
     *
     * @param roomUserGroupDTO the entity to update.
     * @return the persisted entity.
     */
    RoomUserGroupDTO update(RoomUserGroupDTO roomUserGroupDTO);

    /**
     * Partially updates a roomUserGroup.
     *
     * @param roomUserGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomUserGroupDTO> partialUpdate(RoomUserGroupDTO roomUserGroupDTO);

    /**
     * Get all the roomUserGroups.
     *
     * @return the list of entities.
     */
    List<RoomUserGroupDTO> findAll();

    /**
     * Get the "id" roomUserGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomUserGroupDTO> findOne(Long id);

    /**
     * Delete the "id" roomUserGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
