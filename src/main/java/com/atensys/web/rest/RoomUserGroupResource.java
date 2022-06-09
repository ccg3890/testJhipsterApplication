package com.atensys.web.rest;

import com.atensys.repository.RoomUserGroupRepository;
import com.atensys.service.RoomUserGroupService;
import com.atensys.service.dto.RoomUserGroupDTO;
import com.atensys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atensys.domain.RoomUserGroup}.
 */
@RestController
@RequestMapping("/api")
public class RoomUserGroupResource {

    private final Logger log = LoggerFactory.getLogger(RoomUserGroupResource.class);

    private static final String ENTITY_NAME = "roomUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomUserGroupService roomUserGroupService;

    private final RoomUserGroupRepository roomUserGroupRepository;

    public RoomUserGroupResource(RoomUserGroupService roomUserGroupService, RoomUserGroupRepository roomUserGroupRepository) {
        this.roomUserGroupService = roomUserGroupService;
        this.roomUserGroupRepository = roomUserGroupRepository;
    }

    /**
     * {@code POST  /room-user-groups} : Create a new roomUserGroup.
     *
     * @param roomUserGroupDTO the roomUserGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomUserGroupDTO, or with status {@code 400 (Bad Request)} if the roomUserGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-user-groups")
    public ResponseEntity<RoomUserGroupDTO> createRoomUserGroup(@Valid @RequestBody RoomUserGroupDTO roomUserGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save RoomUserGroup : {}", roomUserGroupDTO);
        if (roomUserGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomUserGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomUserGroupDTO result = roomUserGroupService.save(roomUserGroupDTO);
        return ResponseEntity
            .created(new URI("/api/room-user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /room-user-groups/:id} : Updates an existing roomUserGroup.
     *
     * @param id the id of the roomUserGroupDTO to save.
     * @param roomUserGroupDTO the roomUserGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomUserGroupDTO,
     * or with status {@code 400 (Bad Request)} if the roomUserGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomUserGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/room-user-groups/{id}")
    public ResponseEntity<RoomUserGroupDTO> updateRoomUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoomUserGroupDTO roomUserGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoomUserGroup : {}, {}", id, roomUserGroupDTO);
        if (roomUserGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomUserGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomUserGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomUserGroupDTO result = roomUserGroupService.update(roomUserGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomUserGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /room-user-groups/:id} : Partial updates given fields of an existing roomUserGroup, field will ignore if it is null
     *
     * @param id the id of the roomUserGroupDTO to save.
     * @param roomUserGroupDTO the roomUserGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomUserGroupDTO,
     * or with status {@code 400 (Bad Request)} if the roomUserGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roomUserGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomUserGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/room-user-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoomUserGroupDTO> partialUpdateRoomUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoomUserGroupDTO roomUserGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomUserGroup partially : {}, {}", id, roomUserGroupDTO);
        if (roomUserGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomUserGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomUserGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomUserGroupDTO> result = roomUserGroupService.partialUpdate(roomUserGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomUserGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /room-user-groups} : get all the roomUserGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomUserGroups in body.
     */
    @GetMapping("/room-user-groups")
    public List<RoomUserGroupDTO> getAllRoomUserGroups() {
        log.debug("REST request to get all RoomUserGroups");
        return roomUserGroupService.findAll();
    }

    /**
     * {@code GET  /room-user-groups/:id} : get the "id" roomUserGroup.
     *
     * @param id the id of the roomUserGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomUserGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/room-user-groups/{id}")
    public ResponseEntity<RoomUserGroupDTO> getRoomUserGroup(@PathVariable Long id) {
        log.debug("REST request to get RoomUserGroup : {}", id);
        Optional<RoomUserGroupDTO> roomUserGroupDTO = roomUserGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomUserGroupDTO);
    }

    /**
     * {@code DELETE  /room-user-groups/:id} : delete the "id" roomUserGroup.
     *
     * @param id the id of the roomUserGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/room-user-groups/{id}")
    public ResponseEntity<Void> deleteRoomUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete RoomUserGroup : {}", id);
        roomUserGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
