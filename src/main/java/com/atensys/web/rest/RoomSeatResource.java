package com.atensys.web.rest;

import com.atensys.repository.RoomSeatRepository;
import com.atensys.service.RoomSeatService;
import com.atensys.service.dto.RoomSeatDTO;
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
 * REST controller for managing {@link com.atensys.domain.RoomSeat}.
 */
@RestController
@RequestMapping("/api")
public class RoomSeatResource {

    private final Logger log = LoggerFactory.getLogger(RoomSeatResource.class);

    private static final String ENTITY_NAME = "roomSeat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomSeatService roomSeatService;

    private final RoomSeatRepository roomSeatRepository;

    public RoomSeatResource(RoomSeatService roomSeatService, RoomSeatRepository roomSeatRepository) {
        this.roomSeatService = roomSeatService;
        this.roomSeatRepository = roomSeatRepository;
    }

    /**
     * {@code POST  /room-seats} : Create a new roomSeat.
     *
     * @param roomSeatDTO the roomSeatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomSeatDTO, or with status {@code 400 (Bad Request)} if the roomSeat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-seats")
    public ResponseEntity<RoomSeatDTO> createRoomSeat(@Valid @RequestBody RoomSeatDTO roomSeatDTO) throws URISyntaxException {
        log.debug("REST request to save RoomSeat : {}", roomSeatDTO);
        if (roomSeatDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomSeat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomSeatDTO result = roomSeatService.save(roomSeatDTO);
        return ResponseEntity
            .created(new URI("/api/room-seats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /room-seats/:id} : Updates an existing roomSeat.
     *
     * @param id the id of the roomSeatDTO to save.
     * @param roomSeatDTO the roomSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomSeatDTO,
     * or with status {@code 400 (Bad Request)} if the roomSeatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/room-seats/{id}")
    public ResponseEntity<RoomSeatDTO> updateRoomSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoomSeatDTO roomSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoomSeat : {}, {}", id, roomSeatDTO);
        if (roomSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomSeatDTO result = roomSeatService.update(roomSeatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomSeatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /room-seats/:id} : Partial updates given fields of an existing roomSeat, field will ignore if it is null
     *
     * @param id the id of the roomSeatDTO to save.
     * @param roomSeatDTO the roomSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomSeatDTO,
     * or with status {@code 400 (Bad Request)} if the roomSeatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roomSeatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/room-seats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoomSeatDTO> partialUpdateRoomSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoomSeatDTO roomSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomSeat partially : {}, {}", id, roomSeatDTO);
        if (roomSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomSeatDTO> result = roomSeatService.partialUpdate(roomSeatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomSeatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /room-seats} : get all the roomSeats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomSeats in body.
     */
    @GetMapping("/room-seats")
    public List<RoomSeatDTO> getAllRoomSeats() {
        log.debug("REST request to get all RoomSeats");
        return roomSeatService.findAll();
    }

    /**
     * {@code GET  /room-seats/:id} : get the "id" roomSeat.
     *
     * @param id the id of the roomSeatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomSeatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/room-seats/{id}")
    public ResponseEntity<RoomSeatDTO> getRoomSeat(@PathVariable Long id) {
        log.debug("REST request to get RoomSeat : {}", id);
        Optional<RoomSeatDTO> roomSeatDTO = roomSeatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomSeatDTO);
    }

    /**
     * {@code DELETE  /room-seats/:id} : delete the "id" roomSeat.
     *
     * @param id the id of the roomSeatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/room-seats/{id}")
    public ResponseEntity<Void> deleteRoomSeat(@PathVariable Long id) {
        log.debug("REST request to delete RoomSeat : {}", id);
        roomSeatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
