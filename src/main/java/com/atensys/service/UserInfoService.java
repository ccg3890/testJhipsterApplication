package com.atensys.service;

import com.atensys.service.dto.UserInfoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.atensys.domain.UserInfo}.
 */
public interface UserInfoService {
    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save.
     * @return the persisted entity.
     */
    UserInfoDTO save(UserInfoDTO userInfoDTO);

    /**
     * Updates a userInfo.
     *
     * @param userInfoDTO the entity to update.
     * @return the persisted entity.
     */
    UserInfoDTO update(UserInfoDTO userInfoDTO);

    /**
     * Partially updates a userInfo.
     *
     * @param userInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserInfoDTO> partialUpdate(UserInfoDTO userInfoDTO);

    /**
     * Get all the userInfos.
     *
     * @return the list of entities.
     */
    List<UserInfoDTO> findAll();

    /**
     * Get all the userInfos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserInfoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" userInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserInfoDTO> findOne(Long id);

    /**
     * Delete the "id" userInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
