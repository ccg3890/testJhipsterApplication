package com.atensys.service;

import com.atensys.service.dto.AttributeCompanyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.AttributeCompany}.
 */
public interface AttributeCompanyService {
    /**
     * Save a attributeCompany.
     *
     * @param attributeCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeCompanyDTO save(AttributeCompanyDTO attributeCompanyDTO);

    /**
     * Updates a attributeCompany.
     *
     * @param attributeCompanyDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeCompanyDTO update(AttributeCompanyDTO attributeCompanyDTO);

    /**
     * Partially updates a attributeCompany.
     *
     * @param attributeCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeCompanyDTO> partialUpdate(AttributeCompanyDTO attributeCompanyDTO);

    /**
     * Get all the attributeCompanies.
     *
     * @return the list of entities.
     */
    List<AttributeCompanyDTO> findAll();

    /**
     * Get the "id" attributeCompany.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeCompanyDTO> findOne(Long id);

    /**
     * Delete the "id" attributeCompany.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
