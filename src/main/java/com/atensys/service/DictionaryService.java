package com.atensys.service;

import com.atensys.service.dto.DictionaryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Dictionary}.
 */
public interface DictionaryService {
    /**
     * Save a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    DictionaryDTO save(DictionaryDTO dictionaryDTO);

    /**
     * Updates a dictionary.
     *
     * @param dictionaryDTO the entity to update.
     * @return the persisted entity.
     */
    DictionaryDTO update(DictionaryDTO dictionaryDTO);

    /**
     * Partially updates a dictionary.
     *
     * @param dictionaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DictionaryDTO> partialUpdate(DictionaryDTO dictionaryDTO);

    /**
     * Get all the dictionaries.
     *
     * @return the list of entities.
     */
    List<DictionaryDTO> findAll();

    /**
     * Get the "id" dictionary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DictionaryDTO> findOne(Long id);

    /**
     * Delete the "id" dictionary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
