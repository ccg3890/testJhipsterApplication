package com.atensys.web.rest;

import com.atensys.repository.DictionaryRepository;
import com.atensys.service.DictionaryService;
import com.atensys.service.dto.DictionaryDTO;
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
 * REST controller for managing {@link com.atensys.domain.Dictionary}.
 */
@RestController
@RequestMapping("/api")
public class DictionaryResource {

    private final Logger log = LoggerFactory.getLogger(DictionaryResource.class);

    private static final String ENTITY_NAME = "dictionary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DictionaryService dictionaryService;

    private final DictionaryRepository dictionaryRepository;

    public DictionaryResource(DictionaryService dictionaryService, DictionaryRepository dictionaryRepository) {
        this.dictionaryService = dictionaryService;
        this.dictionaryRepository = dictionaryRepository;
    }

    /**
     * {@code POST  /dictionaries} : Create a new dictionary.
     *
     * @param dictionaryDTO the dictionaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dictionaryDTO, or with status {@code 400 (Bad Request)} if the dictionary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dictionaries")
    public ResponseEntity<DictionaryDTO> createDictionary(@Valid @RequestBody DictionaryDTO dictionaryDTO) throws URISyntaxException {
        log.debug("REST request to save Dictionary : {}", dictionaryDTO);
        if (dictionaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new dictionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DictionaryDTO result = dictionaryService.save(dictionaryDTO);
        return ResponseEntity
            .created(new URI("/api/dictionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dictionaries/:id} : Updates an existing dictionary.
     *
     * @param id the id of the dictionaryDTO to save.
     * @param dictionaryDTO the dictionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dictionaryDTO,
     * or with status {@code 400 (Bad Request)} if the dictionaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dictionaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dictionaries/{id}")
    public ResponseEntity<DictionaryDTO> updateDictionary(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DictionaryDTO dictionaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dictionary : {}, {}", id, dictionaryDTO);
        if (dictionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dictionaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dictionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DictionaryDTO result = dictionaryService.update(dictionaryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dictionaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dictionaries/:id} : Partial updates given fields of an existing dictionary, field will ignore if it is null
     *
     * @param id the id of the dictionaryDTO to save.
     * @param dictionaryDTO the dictionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dictionaryDTO,
     * or with status {@code 400 (Bad Request)} if the dictionaryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dictionaryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dictionaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dictionaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DictionaryDTO> partialUpdateDictionary(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DictionaryDTO dictionaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dictionary partially : {}, {}", id, dictionaryDTO);
        if (dictionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dictionaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dictionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DictionaryDTO> result = dictionaryService.partialUpdate(dictionaryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dictionaryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dictionaries} : get all the dictionaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dictionaries in body.
     */
    @GetMapping("/dictionaries")
    public List<DictionaryDTO> getAllDictionaries() {
        log.debug("REST request to get all Dictionaries");
        return dictionaryService.findAll();
    }

    /**
     * {@code GET  /dictionaries/:id} : get the "id" dictionary.
     *
     * @param id the id of the dictionaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dictionaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dictionaries/{id}")
    public ResponseEntity<DictionaryDTO> getDictionary(@PathVariable Long id) {
        log.debug("REST request to get Dictionary : {}", id);
        Optional<DictionaryDTO> dictionaryDTO = dictionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dictionaryDTO);
    }

    /**
     * {@code DELETE  /dictionaries/:id} : delete the "id" dictionary.
     *
     * @param id the id of the dictionaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dictionaries/{id}")
    public ResponseEntity<Void> deleteDictionary(@PathVariable Long id) {
        log.debug("REST request to delete Dictionary : {}", id);
        dictionaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
