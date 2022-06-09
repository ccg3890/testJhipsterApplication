package com.atensys.service;

import com.atensys.service.dto.ConfigDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Config}.
 */
public interface ConfigService {
    /**
     * Save a config.
     *
     * @param configDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigDTO save(ConfigDTO configDTO);

    /**
     * Updates a config.
     *
     * @param configDTO the entity to update.
     * @return the persisted entity.
     */
    ConfigDTO update(ConfigDTO configDTO);

    /**
     * Partially updates a config.
     *
     * @param configDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfigDTO> partialUpdate(ConfigDTO configDTO);

    /**
     * Get all the configs.
     *
     * @return the list of entities.
     */
    List<ConfigDTO> findAll();

    /**
     * Get the "id" config.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigDTO> findOne(Long id);

    /**
     * Delete the "id" config.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
