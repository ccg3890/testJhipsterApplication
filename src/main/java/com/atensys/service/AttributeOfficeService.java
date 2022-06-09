package com.atensys.service;

import com.atensys.service.dto.AttributeOfficeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.AttributeOffice}.
 */
public interface AttributeOfficeService {
    /**
     * Save a attributeOffice.
     *
     * @param attributeOfficeDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeOfficeDTO save(AttributeOfficeDTO attributeOfficeDTO);

    /**
     * Updates a attributeOffice.
     *
     * @param attributeOfficeDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeOfficeDTO update(AttributeOfficeDTO attributeOfficeDTO);

    /**
     * Partially updates a attributeOffice.
     *
     * @param attributeOfficeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeOfficeDTO> partialUpdate(AttributeOfficeDTO attributeOfficeDTO);

    /**
     * Get all the attributeOffices.
     *
     * @return the list of entities.
     */
    List<AttributeOfficeDTO> findAll();

    /**
     * Get the "id" attributeOffice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeOfficeDTO> findOne(Long id);

    /**
     * Delete the "id" attributeOffice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
