package com.atensys.web.rest;

import com.atensys.repository.ReservationTargetRepository;
import com.atensys.service.ReservationTargetService;
import com.atensys.service.dto.ReservationTargetDTO;
import com.atensys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atensys.domain.ReservationTarget}.
 */
@RestController
@RequestMapping("/api")
public class ReservationTargetResource {

    private final Logger log = LoggerFactory.getLogger(ReservationTargetResource.class);

    private static final String ENTITY_NAME = "reservationTarget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationTargetService reservationTargetService;

    private final ReservationTargetRepository reservationTargetRepository;

    public ReservationTargetResource(
        ReservationTargetService reservationTargetService,
        ReservationTargetRepository reservationTargetRepository
    ) {
        this.reservationTargetService = reservationTargetService;
        this.reservationTargetRepository = reservationTargetRepository;
    }

    /**
     * {@code POST  /reservation-targets} : Create a new reservationTarget.
     *
     * @param reservationTargetDTO the reservationTargetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationTargetDTO, or with status {@code 400 (Bad Request)} if the reservationTarget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservation-targets")
    public ResponseEntity<ReservationTargetDTO> createReservationTarget(@RequestBody ReservationTargetDTO reservationTargetDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReservationTarget : {}", reservationTargetDTO);
        if (reservationTargetDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservationTarget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationTargetDTO result = reservationTargetService.save(reservationTargetDTO);
        return ResponseEntity
            .created(new URI("/api/reservation-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-targets/:id} : Updates an existing reservationTarget.
     *
     * @param id the id of the reservationTargetDTO to save.
     * @param reservationTargetDTO the reservationTargetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTargetDTO,
     * or with status {@code 400 (Bad Request)} if the reservationTargetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationTargetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservation-targets/{id}")
    public ResponseEntity<ReservationTargetDTO> updateReservationTarget(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationTargetDTO reservationTargetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReservationTarget : {}, {}", id, reservationTargetDTO);
        if (reservationTargetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationTargetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationTargetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservationTargetDTO result = reservationTargetService.update(reservationTargetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTargetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reservation-targets/:id} : Partial updates given fields of an existing reservationTarget, field will ignore if it is null
     *
     * @param id the id of the reservationTargetDTO to save.
     * @param reservationTargetDTO the reservationTargetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTargetDTO,
     * or with status {@code 400 (Bad Request)} if the reservationTargetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservationTargetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservationTargetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reservation-targets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservationTargetDTO> partialUpdateReservationTarget(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationTargetDTO reservationTargetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReservationTarget partially : {}, {}", id, reservationTargetDTO);
        if (reservationTargetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationTargetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationTargetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservationTargetDTO> result = reservationTargetService.partialUpdate(reservationTargetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTargetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reservation-targets} : get all the reservationTargets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationTargets in body.
     */
    @GetMapping("/reservation-targets")
    public List<ReservationTargetDTO> getAllReservationTargets() {
        log.debug("REST request to get all ReservationTargets");
        return reservationTargetService.findAll();
    }

    /**
     * {@code GET  /reservation-targets/:id} : get the "id" reservationTarget.
     *
     * @param id the id of the reservationTargetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationTargetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservation-targets/{id}")
    public ResponseEntity<ReservationTargetDTO> getReservationTarget(@PathVariable Long id) {
        log.debug("REST request to get ReservationTarget : {}", id);
        Optional<ReservationTargetDTO> reservationTargetDTO = reservationTargetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservationTargetDTO);
    }

    /**
     * {@code DELETE  /reservation-targets/:id} : delete the "id" reservationTarget.
     *
     * @param id the id of the reservationTargetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservation-targets/{id}")
    public ResponseEntity<Void> deleteReservationTarget(@PathVariable Long id) {
        log.debug("REST request to delete ReservationTarget : {}", id);
        reservationTargetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
