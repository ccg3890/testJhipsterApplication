package com.atensys.service;

import com.atensys.service.dto.RankDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.atensys.domain.Rank}.
 */
public interface RankService {
    /**
     * Save a rank.
     *
     * @param rankDTO the entity to save.
     * @return the persisted entity.
     */
    RankDTO save(RankDTO rankDTO);

    /**
     * Updates a rank.
     *
     * @param rankDTO the entity to update.
     * @return the persisted entity.
     */
    RankDTO update(RankDTO rankDTO);

    /**
     * Partially updates a rank.
     *
     * @param rankDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RankDTO> partialUpdate(RankDTO rankDTO);

    /**
     * Get all the ranks.
     *
     * @return the list of entities.
     */
    List<RankDTO> findAll();

    /**
     * Get the "id" rank.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RankDTO> findOne(Long id);

    /**
     * Delete the "id" rank.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
