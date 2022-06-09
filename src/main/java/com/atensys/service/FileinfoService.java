package com.atensys.service;

import com.atensys.service.dto.FileinfoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Fileinfo}.
 */
public interface FileinfoService {
    /**
     * Save a fileinfo.
     *
     * @param fileinfoDTO the entity to save.
     * @return the persisted entity.
     */
    FileinfoDTO save(FileinfoDTO fileinfoDTO);

    /**
     * Updates a fileinfo.
     *
     * @param fileinfoDTO the entity to update.
     * @return the persisted entity.
     */
    FileinfoDTO update(FileinfoDTO fileinfoDTO);

    /**
     * Partially updates a fileinfo.
     *
     * @param fileinfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileinfoDTO> partialUpdate(FileinfoDTO fileinfoDTO);

    /**
     * Get all the fileinfos.
     *
     * @return the list of entities.
     */
    List<FileinfoDTO> findAll();

    /**
     * Get the "id" fileinfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileinfoDTO> findOne(Long id);

    /**
     * Delete the "id" fileinfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
