package com.atensys.service;

import com.atensys.service.dto.OfficeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Office}.
 */
public interface OfficeService {
    /**
     * Save a office.
     *
     * @param officeDTO the entity to save.
     * @return the persisted entity.
     */
    OfficeDTO save(OfficeDTO officeDTO);

    /**
     * Updates a office.
     *
     * @param officeDTO the entity to update.
     * @return the persisted entity.
     */
    OfficeDTO update(OfficeDTO officeDTO);

    /**
     * Partially updates a office.
     *
     * @param officeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfficeDTO> partialUpdate(OfficeDTO officeDTO);

    /**
     * Get all the offices.
     *
     * @return the list of entities.
     */
    List<OfficeDTO> findAll();

    /**
     * Get the "id" office.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficeDTO> findOne(Long id);

    /**
     * Delete the "id" office.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
