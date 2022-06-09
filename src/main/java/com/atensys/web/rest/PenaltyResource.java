package com.atensys.web.rest;

import com.atensys.repository.PenaltyRepository;
import com.atensys.service.PenaltyService;
import com.atensys.service.dto.PenaltyDTO;
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
 * REST controller for managing {@link com.atensys.domain.Penalty}.
 */
@RestController
@RequestMapping("/api")
public class PenaltyResource {

    private final Logger log = LoggerFactory.getLogger(PenaltyResource.class);

    private static final String ENTITY_NAME = "penalty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PenaltyService penaltyService;

    private final PenaltyRepository penaltyRepository;

    public PenaltyResource(PenaltyService penaltyService, PenaltyRepository penaltyRepository) {
        this.penaltyService = penaltyService;
        this.penaltyRepository = penaltyRepository;
    }

    /**
     * {@code POST  /penalties} : Create a new penalty.
     *
     * @param penaltyDTO the penaltyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new penaltyDTO, or with status {@code 400 (Bad Request)} if the penalty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/penalties")
    public ResponseEntity<PenaltyDTO> createPenalty(@Valid @RequestBody PenaltyDTO penaltyDTO) throws URISyntaxException {
        log.debug("REST request to save Penalty : {}", penaltyDTO);
        if (penaltyDTO.getId() != null) {
            throw new BadRequestAlertException("A new penalty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PenaltyDTO result = penaltyService.save(penaltyDTO);
        return ResponseEntity
            .created(new URI("/api/penalties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /penalties/:id} : Updates an existing penalty.
     *
     * @param id the id of the penaltyDTO to save.
     * @param penaltyDTO the penaltyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated penaltyDTO,
     * or with status {@code 400 (Bad Request)} if the penaltyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the penaltyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/penalties/{id}")
    public ResponseEntity<PenaltyDTO> updatePenalty(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PenaltyDTO penaltyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Penalty : {}, {}", id, penaltyDTO);
        if (penaltyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, penaltyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!penaltyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PenaltyDTO result = penaltyService.update(penaltyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, penaltyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /penalties/:id} : Partial updates given fields of an existing penalty, field will ignore if it is null
     *
     * @param id the id of the penaltyDTO to save.
     * @param penaltyDTO the penaltyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated penaltyDTO,
     * or with status {@code 400 (Bad Request)} if the penaltyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the penaltyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the penaltyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/penalties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PenaltyDTO> partialUpdatePenalty(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PenaltyDTO penaltyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Penalty partially : {}, {}", id, penaltyDTO);
        if (penaltyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, penaltyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!penaltyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PenaltyDTO> result = penaltyService.partialUpdate(penaltyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, penaltyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /penalties} : get all the penalties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of penalties in body.
     */
    @GetMapping("/penalties")
    public List<PenaltyDTO> getAllPenalties() {
        log.debug("REST request to get all Penalties");
        return penaltyService.findAll();
    }

    /**
     * {@code GET  /penalties/:id} : get the "id" penalty.
     *
     * @param id the id of the penaltyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the penaltyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/penalties/{id}")
    public ResponseEntity<PenaltyDTO> getPenalty(@PathVariable Long id) {
        log.debug("REST request to get Penalty : {}", id);
        Optional<PenaltyDTO> penaltyDTO = penaltyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(penaltyDTO);
    }

    /**
     * {@code DELETE  /penalties/:id} : delete the "id" penalty.
     *
     * @param id the id of the penaltyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/penalties/{id}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Long id) {
        log.debug("REST request to delete Penalty : {}", id);
        penaltyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
