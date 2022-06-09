package com.atensys.service;

import com.atensys.service.dto.AuthDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.atensys.domain.Auth}.
 */
public interface AuthService {
    /**
     * Save a auth.
     *
     * @param authDTO the entity to save.
     * @return the persisted entity.
     */
    AuthDTO save(AuthDTO authDTO);

    /**
     * Updates a auth.
     *
     * @param authDTO the entity to update.
     * @return the persisted entity.
     */
    AuthDTO update(AuthDTO authDTO);

    /**
     * Partially updates a auth.
     *
     * @param authDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuthDTO> partialUpdate(AuthDTO authDTO);

    /**
     * Get all the auths.
     *
     * @return the list of entities.
     */
    List<AuthDTO> findAll();

    /**
     * Get all the auths with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuthDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" auth.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthDTO> findOne(Long id);

    /**
     * Delete the "id" auth.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
