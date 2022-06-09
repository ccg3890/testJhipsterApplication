package com.atensys.web.rest;

import com.atensys.repository.RoomManagerRepository;
import com.atensys.service.RoomManagerService;
import com.atensys.service.dto.RoomManagerDTO;
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
 * REST controller for managing {@link com.atensys.domain.RoomManager}.
 */
@RestController
@RequestMapping("/api")
public class RoomManagerResource {

    private final Logger log = LoggerFactory.getLogger(RoomManagerResource.class);

    private static final String ENTITY_NAME = "roomManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomManagerService roomManagerService;

    private final RoomManagerRepository roomManagerRepository;

    public RoomManagerResource(RoomManagerService roomManagerService, RoomManagerRepository roomManagerRepository) {
        this.roomManagerService = roomManagerService;
        this.roomManagerRepository = roomManagerRepository;
    }

    /**
     * {@code POST  /room-managers} : Create a new roomManager.
     *
     * @param roomManagerDTO the roomManagerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomManagerDTO, or with status {@code 400 (Bad Request)} if the roomManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-managers")
    public ResponseEntity<RoomManagerDTO> createRoomManager(@Valid @RequestBody RoomManagerDTO roomManagerDTO) throws URISyntaxException {
        log.debug("REST request to save RoomManager : {}", roomManagerDTO);
        if (roomManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomManagerDTO result = roomManagerService.save(roomManagerDTO);
        return ResponseEntity
            .created(new URI("/api/room-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /room-managers/:id} : Updates an existing roomManager.
     *
     * @param id the id of the roomManagerDTO to save.
     * @param roomManagerDTO the roomManagerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomManagerDTO,
     * or with status {@code 400 (Bad Request)} if the roomManagerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomManagerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/room-managers/{id}")
    public ResponseEntity<RoomManagerDTO> updateRoomManager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoomManagerDTO roomManagerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoomManager : {}, {}", id, roomManagerDTO);
        if (roomManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomManagerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomManagerDTO result = roomManagerService.update(roomManagerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /room-managers/:id} : Partial updates given fields of an existing roomManager, field will ignore if it is null
     *
     * @param id the id of the roomManagerDTO to save.
     * @param roomManagerDTO the roomManagerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomManagerDTO,
     * or with status {@code 400 (Bad Request)} if the roomManagerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roomManagerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomManagerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/room-managers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoomManagerDTO> partialUpdateRoomManager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoomManagerDTO roomManagerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomManager partially : {}, {}", id, roomManagerDTO);
        if (roomManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomManagerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomManagerDTO> result = roomManagerService.partialUpdate(roomManagerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomManagerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /room-managers} : get all the roomManagers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomManagers in body.
     */
    @GetMapping("/room-managers")
    public List<RoomManagerDTO> getAllRoomManagers() {
        log.debug("REST request to get all RoomManagers");
        return roomManagerService.findAll();
    }

    /**
     * {@code GET  /room-managers/:id} : get the "id" roomManager.
     *
     * @param id the id of the roomManagerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomManagerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/room-managers/{id}")
    public ResponseEntity<RoomManagerDTO> getRoomManager(@PathVariable Long id) {
        log.debug("REST request to get RoomManager : {}", id);
        Optional<RoomManagerDTO> roomManagerDTO = roomManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomManagerDTO);
    }

    /**
     * {@code DELETE  /room-managers/:id} : delete the "id" roomManager.
     *
     * @param id the id of the roomManagerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/room-managers/{id}")
    public ResponseEntity<Void> deleteRoomManager(@PathVariable Long id) {
        log.debug("REST request to delete RoomManager : {}", id);
        roomManagerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
