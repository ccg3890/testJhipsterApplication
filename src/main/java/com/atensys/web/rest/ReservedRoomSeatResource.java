package com.atensys.web.rest;

import com.atensys.repository.ReservedRoomSeatRepository;
import com.atensys.service.ReservedRoomSeatService;
import com.atensys.service.dto.ReservedRoomSeatDTO;
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
 * REST controller for managing {@link com.atensys.domain.ReservedRoomSeat}.
 */
@RestController
@RequestMapping("/api")
public class ReservedRoomSeatResource {

    private final Logger log = LoggerFactory.getLogger(ReservedRoomSeatResource.class);

    private static final String ENTITY_NAME = "reservedRoomSeat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservedRoomSeatService reservedRoomSeatService;

    private final ReservedRoomSeatRepository reservedRoomSeatRepository;

    public ReservedRoomSeatResource(
        ReservedRoomSeatService reservedRoomSeatService,
        ReservedRoomSeatRepository reservedRoomSeatRepository
    ) {
        this.reservedRoomSeatService = reservedRoomSeatService;
        this.reservedRoomSeatRepository = reservedRoomSeatRepository;
    }

    /**
     * {@code POST  /reserved-room-seats} : Create a new reservedRoomSeat.
     *
     * @param reservedRoomSeatDTO the reservedRoomSeatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservedRoomSeatDTO, or with status {@code 400 (Bad Request)} if the reservedRoomSeat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reserved-room-seats")
    public ResponseEntity<ReservedRoomSeatDTO> createReservedRoomSeat(@Valid @RequestBody ReservedRoomSeatDTO reservedRoomSeatDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReservedRoomSeat : {}", reservedRoomSeatDTO);
        if (reservedRoomSeatDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservedRoomSeat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservedRoomSeatDTO result = reservedRoomSeatService.save(reservedRoomSeatDTO);
        return ResponseEntity
            .created(new URI("/api/reserved-room-seats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reserved-room-seats/:id} : Updates an existing reservedRoomSeat.
     *
     * @param id the id of the reservedRoomSeatDTO to save.
     * @param reservedRoomSeatDTO the reservedRoomSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservedRoomSeatDTO,
     * or with status {@code 400 (Bad Request)} if the reservedRoomSeatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservedRoomSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reserved-room-seats/{id}")
    public ResponseEntity<ReservedRoomSeatDTO> updateReservedRoomSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReservedRoomSeatDTO reservedRoomSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReservedRoomSeat : {}, {}", id, reservedRoomSeatDTO);
        if (reservedRoomSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservedRoomSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservedRoomSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservedRoomSeatDTO result = reservedRoomSeatService.update(reservedRoomSeatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservedRoomSeatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reserved-room-seats/:id} : Partial updates given fields of an existing reservedRoomSeat, field will ignore if it is null
     *
     * @param id the id of the reservedRoomSeatDTO to save.
     * @param reservedRoomSeatDTO the reservedRoomSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservedRoomSeatDTO,
     * or with status {@code 400 (Bad Request)} if the reservedRoomSeatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservedRoomSeatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservedRoomSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reserved-room-seats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservedRoomSeatDTO> partialUpdateReservedRoomSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReservedRoomSeatDTO reservedRoomSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReservedRoomSeat partially : {}, {}", id, reservedRoomSeatDTO);
        if (reservedRoomSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservedRoomSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservedRoomSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservedRoomSeatDTO> result = reservedRoomSeatService.partialUpdate(reservedRoomSeatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservedRoomSeatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reserved-room-seats} : get all the reservedRoomSeats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservedRoomSeats in body.
     */
    @GetMapping("/reserved-room-seats")
    public List<ReservedRoomSeatDTO> getAllReservedRoomSeats() {
        log.debug("REST request to get all ReservedRoomSeats");
        return reservedRoomSeatService.findAll();
    }

    /**
     * {@code GET  /reserved-room-seats/:id} : get the "id" reservedRoomSeat.
     *
     * @param id the id of the reservedRoomSeatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservedRoomSeatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reserved-room-seats/{id}")
    public ResponseEntity<ReservedRoomSeatDTO> getReservedRoomSeat(@PathVariable Long id) {
        log.debug("REST request to get ReservedRoomSeat : {}", id);
        Optional<ReservedRoomSeatDTO> reservedRoomSeatDTO = reservedRoomSeatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservedRoomSeatDTO);
    }

    /**
     * {@code DELETE  /reserved-room-seats/:id} : delete the "id" reservedRoomSeat.
     *
     * @param id the id of the reservedRoomSeatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reserved-room-seats/{id}")
    public ResponseEntity<Void> deleteReservedRoomSeat(@PathVariable Long id) {
        log.debug("REST request to delete ReservedRoomSeat : {}", id);
        reservedRoomSeatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
