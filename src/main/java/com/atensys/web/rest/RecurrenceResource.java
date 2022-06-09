package com.atensys.web.rest;

import com.atensys.repository.RecurrenceRepository;
import com.atensys.service.RecurrenceService;
import com.atensys.service.dto.RecurrenceDTO;
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
 * REST controller for managing {@link com.atensys.domain.Recurrence}.
 */
@RestController
@RequestMapping("/api")
public class RecurrenceResource {

    private final Logger log = LoggerFactory.getLogger(RecurrenceResource.class);

    private static final String ENTITY_NAME = "recurrence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecurrenceService recurrenceService;

    private final RecurrenceRepository recurrenceRepository;

    public RecurrenceResource(RecurrenceService recurrenceService, RecurrenceRepository recurrenceRepository) {
        this.recurrenceService = recurrenceService;
        this.recurrenceRepository = recurrenceRepository;
    }

    /**
     * {@code POST  /recurrences} : Create a new recurrence.
     *
     * @param recurrenceDTO the recurrenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recurrenceDTO, or with status {@code 400 (Bad Request)} if the recurrence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurrences")
    public ResponseEntity<RecurrenceDTO> createRecurrence(@Valid @RequestBody RecurrenceDTO recurrenceDTO) throws URISyntaxException {
        log.debug("REST request to save Recurrence : {}", recurrenceDTO);
        if (recurrenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new recurrence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecurrenceDTO result = recurrenceService.save(recurrenceDTO);
        return ResponseEntity
            .created(new URI("/api/recurrences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurrences/:id} : Updates an existing recurrence.
     *
     * @param id the id of the recurrenceDTO to save.
     * @param recurrenceDTO the recurrenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurrenceDTO,
     * or with status {@code 400 (Bad Request)} if the recurrenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recurrenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurrences/{id}")
    public ResponseEntity<RecurrenceDTO> updateRecurrence(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecurrenceDTO recurrenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Recurrence : {}, {}", id, recurrenceDTO);
        if (recurrenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurrenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurrenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecurrenceDTO result = recurrenceService.update(recurrenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recurrenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recurrences/:id} : Partial updates given fields of an existing recurrence, field will ignore if it is null
     *
     * @param id the id of the recurrenceDTO to save.
     * @param recurrenceDTO the recurrenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurrenceDTO,
     * or with status {@code 400 (Bad Request)} if the recurrenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recurrenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recurrenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recurrences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecurrenceDTO> partialUpdateRecurrence(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecurrenceDTO recurrenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recurrence partially : {}, {}", id, recurrenceDTO);
        if (recurrenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurrenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurrenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecurrenceDTO> result = recurrenceService.partialUpdate(recurrenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recurrenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recurrences} : get all the recurrences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurrences in body.
     */
    @GetMapping("/recurrences")
    public List<RecurrenceDTO> getAllRecurrences() {
        log.debug("REST request to get all Recurrences");
        return recurrenceService.findAll();
    }

    /**
     * {@code GET  /recurrences/:id} : get the "id" recurrence.
     *
     * @param id the id of the recurrenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recurrenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurrences/{id}")
    public ResponseEntity<RecurrenceDTO> getRecurrence(@PathVariable Long id) {
        log.debug("REST request to get Recurrence : {}", id);
        Optional<RecurrenceDTO> recurrenceDTO = recurrenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recurrenceDTO);
    }

    /**
     * {@code DELETE  /recurrences/:id} : delete the "id" recurrence.
     *
     * @param id the id of the recurrenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurrences/{id}")
    public ResponseEntity<Void> deleteRecurrence(@PathVariable Long id) {
        log.debug("REST request to delete Recurrence : {}", id);
        recurrenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
