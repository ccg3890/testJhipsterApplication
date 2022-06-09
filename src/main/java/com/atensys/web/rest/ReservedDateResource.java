package com.atensys.web.rest;

import com.atensys.repository.ReservedDateRepository;
import com.atensys.service.ReservedDateService;
import com.atensys.service.dto.ReservedDateDTO;
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
 * REST controller for managing {@link com.atensys.domain.ReservedDate}.
 */
@RestController
@RequestMapping("/api")
public class ReservedDateResource {

    private final Logger log = LoggerFactory.getLogger(ReservedDateResource.class);

    private static final String ENTITY_NAME = "reservedDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservedDateService reservedDateService;

    private final ReservedDateRepository reservedDateRepository;

    public ReservedDateResource(ReservedDateService reservedDateService, ReservedDateRepository reservedDateRepository) {
        this.reservedDateService = reservedDateService;
        this.reservedDateRepository = reservedDateRepository;
    }

    /**
     * {@code POST  /reserved-dates} : Create a new reservedDate.
     *
     * @param reservedDateDTO the reservedDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservedDateDTO, or with status {@code 400 (Bad Request)} if the reservedDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reserved-dates")
    public ResponseEntity<ReservedDateDTO> createReservedDate(@Valid @RequestBody ReservedDateDTO reservedDateDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReservedDate : {}", reservedDateDTO);
        if (reservedDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservedDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservedDateDTO result = reservedDateService.save(reservedDateDTO);
        return ResponseEntity
            .created(new URI("/api/reserved-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reserved-dates/:id} : Updates an existing reservedDate.
     *
     * @param id the id of the reservedDateDTO to save.
     * @param reservedDateDTO the reservedDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservedDateDTO,
     * or with status {@code 400 (Bad Request)} if the reservedDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservedDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reserved-dates/{id}")
    public ResponseEntity<ReservedDateDTO> updateReservedDate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReservedDateDTO reservedDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReservedDate : {}, {}", id, reservedDateDTO);
        if (reservedDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservedDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservedDateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservedDateDTO result = reservedDateService.update(reservedDateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservedDateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reserved-dates/:id} : Partial updates given fields of an existing reservedDate, field will ignore if it is null
     *
     * @param id the id of the reservedDateDTO to save.
     * @param reservedDateDTO the reservedDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservedDateDTO,
     * or with status {@code 400 (Bad Request)} if the reservedDateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservedDateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservedDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reserved-dates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservedDateDTO> partialUpdateReservedDate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReservedDateDTO reservedDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReservedDate partially : {}, {}", id, reservedDateDTO);
        if (reservedDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservedDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservedDateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservedDateDTO> result = reservedDateService.partialUpdate(reservedDateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservedDateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reserved-dates} : get all the reservedDates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservedDates in body.
     */
    @GetMapping("/reserved-dates")
    public List<ReservedDateDTO> getAllReservedDates() {
        log.debug("REST request to get all ReservedDates");
        return reservedDateService.findAll();
    }

    /**
     * {@code GET  /reserved-dates/:id} : get the "id" reservedDate.
     *
     * @param id the id of the reservedDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservedDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reserved-dates/{id}")
    public ResponseEntity<ReservedDateDTO> getReservedDate(@PathVariable Long id) {
        log.debug("REST request to get ReservedDate : {}", id);
        Optional<ReservedDateDTO> reservedDateDTO = reservedDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservedDateDTO);
    }

    /**
     * {@code DELETE  /reserved-dates/:id} : delete the "id" reservedDate.
     *
     * @param id the id of the reservedDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reserved-dates/{id}")
    public ResponseEntity<Void> deleteReservedDate(@PathVariable Long id) {
        log.debug("REST request to delete ReservedDate : {}", id);
        reservedDateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
